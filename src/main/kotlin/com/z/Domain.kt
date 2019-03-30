package com.z

import java.time.LocalDateTime

data class User(val id:Int,val name:String)

data class Task(val id:Int,val description:String, val creation:LocalDateTime = LocalDateTime.now())