package com.z

import java.util.*
import kotlin.concurrent.thread

abstract class PublishAndSleep:Publisher{
    abstract val topic:String
    init {
        thread(true) {
            while (true){
                val random = (5000..10000).random()
                Thread.sleep(random.toLong())
                this.send(random)
            }
        }
    }
    abstract fun send(randomInt: Int)
}

class RandomUserPublisher(override  val topic: String):PublishAndSleep() {
    override fun send(randomInt: Int) {
        this.publish(topic,User(randomInt,"this is a random string ${UUID.randomUUID()}"))
    }
}

class RandomTaskPublisher(override val topic: String):PublishAndSleep() {
    override fun send(randomInt: Int) {
        this.publish(topic,Task(randomInt,"order from ${Thread.currentThread().name}"))
    }
}



