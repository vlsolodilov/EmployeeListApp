package com.solodilov.employeelistapp.data.network.model

import com.google.gson.annotations.SerializedName
import com.solodilov.employeelistapp.data.database.entity.EmployeeDb
import com.solodilov.employeelistapp.domain.entity.Employee
import java.time.LocalDate

data class EmployeeDto(
    @SerializedName("avatarUrl")
    val avatarUrl: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("department")
    val department: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("userTag")
    val userTag: String
)

fun EmployeeDto.toEmployee() = Employee(
    id = id,
    firstName = firstName,
    lastName = lastName,
    avatarUrl = avatarUrl,
    birthday = try {
        if (birthday.isNotBlank()) LocalDate.parse(birthday) else null
    } catch (e: Exception) {
        null
    },
    department = enumValueOf(department.uppercase()),
    phone = phone,
    position = position,
    userTag = userTag,
)

fun EmployeeDto.toEmployeeDb() = EmployeeDb(
    id = id,
    firstName = firstName,
    lastName = lastName,
    avatarUrl = avatarUrl,
    birthday = birthday,
    department = department,
    phone = phone,
    position = position,
    userTag = userTag,
)