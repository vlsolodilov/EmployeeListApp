package com.solodilov.employeelistapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Employee(
    val id: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val birthday: LocalDate?,
    val department: Department,
    val phone: String,
    val position: String,
    val userTag: String
): Parcelable