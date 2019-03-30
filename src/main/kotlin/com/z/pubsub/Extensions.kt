package com.z.pubsub

import com.z.pubsub.interfaces.Publisher

fun Publisher.publish(topic: String, data: Any){
    PublisherSubscriberService.publish(topic,data)
}