package com.example.practicalexam.api

import com.example.practicalexam.dashboardclass.DashboardRequest
import com.example.practicalexam.dashboardclass.DashboardResponse
import com.example.practicalexam.modelclass.UserRequest
import com.example.practicalexam.modelclass.UserResponse
import com.example.practicalexam.profileDetails.DetailsRequest
import com.example.practicalexam.profileDetails.DetailsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {

    @POST("user-list")
    fun sendUserData(
        @HeaderMap headers: Map<String,String>,
        @Body userRequest: UserRequest
    ) : Call<UserResponse>

    @POST("get-customer-data-mobile")
    fun sendDashboardData(
        @HeaderMap headers: Map<String,String>,
        @Body userRequest: DashboardRequest
    ) : Call<DashboardResponse>

    @POST("get-customer-data-mobile")
    fun sendProfileData(
        @HeaderMap headers: Map<String,String>,
        @Body detailsRequest: DetailsRequest
    ) : Call<DetailsResponse>

//    @PUT("api/users/2")
//    Call<DataModal> updateData(
//    @Body DataModal dataModal)



}