package com.z

import java.util.concurrent.ConcurrentHashMap

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class SubscribeTo(val topic: String)

interface Subscriber{ fun onNext(topic: String, data: Any) }

interface Publisher

fun Publisher.publish(topic: String,data: Any){
    PublisherSubscriberService.publish(topic,data)
}

object PublisherSubscriberService {
    private val subscribersByTopic =  ConcurrentHashMap<String,MutableSet<Subscriber>>()

    private fun addSubscriber(subscriber: Subscriber, vararg topics: String){
        topics.forEach {
            subscribersByTopic[it] = (subscribersByTopic[it] ?: mutableSetOf()).apply {
                add(subscriber)
                println("New subscriber: ${subscriber.javaClass.name} - topic: $it")
            }
        }
    }

    fun publish(topic: String, data: Any){ subscribersByTopic[topic]?.forEach { it.onNext(topic,data) } }

     fun bind(vararg targets: Any) {
         targets.forEach { target ->
             target::class.java.declaredMethods.forEach { method ->
                 method.annotations.forEach { annotation ->
                     when (annotation) {
                         is SubscribeTo -> {
                             PublisherSubscriberService.addSubscriber(object : Subscriber {
                                 override fun onNext(topic: String, data: Any) { method.invoke(target, data) }
                             }, annotation.topic)
                         }
                     }
                 }
             }
         }
     }
}