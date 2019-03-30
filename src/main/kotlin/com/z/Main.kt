package com.z

const val NEW_USER_TOPIC = "USER"
const val NEW_TASK_TOPIC = "TASK"

class SubscriberOne{
    @SubscribeTo(NEW_USER_TOPIC)
    fun onNewUser(user: User) {
        println("SubscriberOne.onNewUser: $user")
    }

    @SubscribeTo(NEW_TASK_TOPIC)
    fun onNewTask(task: Task) {
        println("SubscriberOne.task: $task")
    }
}

class SubscriberTwo{
    @SubscribeTo(NEW_TASK_TOPIC)
    fun onNewTask(task: Task) {
        println("SubscriberTwo.task: $task")
    }
}

fun main(args: Array<String>) {
    PublisherSubscriberService.bind(SubscriberOne(),SubscriberTwo())
    val publisher1 = RandomUserPublisher(NEW_USER_TOPIC)
    val publisher2 = RandomTaskPublisher(NEW_TASK_TOPIC)
}