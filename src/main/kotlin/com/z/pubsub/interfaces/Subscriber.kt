package com.z.pubsub.interfaces

interface Subscriber{ fun onNext(topic: String, data: Any) }