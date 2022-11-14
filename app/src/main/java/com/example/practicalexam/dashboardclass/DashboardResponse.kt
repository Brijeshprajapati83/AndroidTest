package com.example.practicalexam.dashboardclass

data class DashboardResponse(
    val data: List<DataDash>,
    val message: String,
    val status_code: Int,
    val status_message: String
)