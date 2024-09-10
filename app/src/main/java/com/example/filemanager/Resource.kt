package com.example.filemanager

sealed class Resource(val status: Status? = null, val message: String? = null) {
    object NotStarted : Resource()
    class Running(status: Status): Resource(status)
    class Success(message: String): Resource(message= message)
}


data class Status(
    val progress: Float,
    val speed: Float
)
//You Can Catch Me From The Links Below
//Fiverr Link: https://www.fiverr.com/tonyappdevelopr
//LinkedIn Link: https://www.linkedin.com/in/ahsan-tony-b8ba94325