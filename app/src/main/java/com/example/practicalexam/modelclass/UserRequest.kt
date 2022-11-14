package com.example.practicalexam.modelclass

data class UserRequest(
    val device_type: String,
    val entity_type_id: String,
    val user_id: String
)