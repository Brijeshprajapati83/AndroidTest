package com.example.practicalexam.profileDetails

data class DetailsRequest(
    val device_type: String,
    val entity_type_id: String,
    val user_id: String
)