package com.solodilov.employeelistapp.data.network.model

import com.google.gson.annotations.SerializedName

data class EmployeeListDto(
    @SerializedName("items")
    val items: List<EmployeeDto>
)