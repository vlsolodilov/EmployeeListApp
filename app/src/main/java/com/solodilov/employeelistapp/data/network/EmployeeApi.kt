package com.solodilov.employeelistapp.data.network

import com.solodilov.employeelistapp.data.network.model.EmployeeListDto
import retrofit2.http.GET

interface EmployeeApi {

    @GET("users")
    suspend fun getEmployees(): EmployeeListDto

}