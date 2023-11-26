package com.solodilov.employeelistapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solodilov.employeelistapp.domain.entity.Employee
import java.time.LocalDate

@Entity(tableName = "employee")
data class EmployeeDb(
    @PrimaryKey
    val id: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val birthday: String,
    val department: String,
    val phone: String,
    val position: String,
    val userTag: String,
)

fun EmployeeDb.toEmployee() = Employee(
    id = id,
    firstName = firstName,
    lastName = lastName,
    avatarUrl = avatarUrl,
    birthday = try {
        LocalDate.parse(birthday)
    } catch (e: Exception) {
        null
    },
    department = enumValueOf(department.uppercase()),
    phone = phone,
    position = position,
    userTag = userTag,
)