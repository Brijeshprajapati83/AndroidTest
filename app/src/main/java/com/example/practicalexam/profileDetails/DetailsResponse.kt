package com.example.practicalexam.profileDetails

data class DetailsResponse(
    val data: List<DataDetails>,
    val message: String,
    val status_code: Int,
    val status_message: String
)