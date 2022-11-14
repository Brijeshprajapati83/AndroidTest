package com.example.practicalexam.modelclass

data class UserResponse(
    val data: Data,
    val message: String,
    val status_code: Int,
    val status_message: String
)