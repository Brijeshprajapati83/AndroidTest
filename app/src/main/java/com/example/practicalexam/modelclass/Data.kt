package com.example.practicalexam.modelclass

data class Data(
    val next: String,
    val previous: String,
    val total_pages: Int,
    val total_records: Int,
    val user_data: List<UserData>
)