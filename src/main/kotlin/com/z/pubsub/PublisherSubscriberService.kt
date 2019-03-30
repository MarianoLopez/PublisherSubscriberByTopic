package com.z.pubsub

import com.z.pubsub.annotations.SubscribeTo
import com.z.pubsub.data.transfer.SubscriberReflectionWrapper
import com.z.pubsub.exceptions.BindException
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

object PublisherSubscriberService {
    private val subscribersByTopic =  ConcurrentHashMap<String,MutableSet<SubscriberReflectionWrapper>>()
    private val executors = Executors.newCachedThreadPool()

    private fun addSubscriber(target: Any,method: Method, vararg topics: String){
        topics.forEach {
            subscribersByTopic[it] = (subscribersByTopic[it] ?: mutableSetOf()).apply {
                add(SubscriberReflectionWrapper(target, method))
                println("New subscriber: ${target::class.java.name}.${method.name} on topic: $it")
            }
        }
    }

    fun publish(topic: String, data: Any){
        executors.submit {
            subscribersByTopic[topic]?.forEach { it.method.invoke(it.target,data) }
        }
    }

     fun bind(vararg targets: Any) {
         targets.forEach { target ->
             val clazz = target::class.java
             clazz.declaredMethods.forEach { method ->
                 method.annotations.forEach { annotation ->
                     when (annotation) {
                         is SubscribeTo -> {
                             if(!Modifier.isPublic(method.modifiers)){
                                 throw BindException("Method $clazz.${method.name} should be public")
                             }else{
                                 addSubscriber(target, method, annotation.topic)
                             }

                         }
                     }
                 }
             }
         }
     }
}