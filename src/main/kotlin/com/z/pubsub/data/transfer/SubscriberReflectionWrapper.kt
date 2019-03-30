package com.z.pubsub.data.transfer

import java.lang.reflect.Method

data class SubscriberReflectionWrapper(val target:Any, val method: Method)