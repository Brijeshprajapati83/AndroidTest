package com.example.practicalexam.dashboardclass

data class DashboardRequest(
    val device_type: String,
    val entity_type_id: String,
    val user_id: String
)