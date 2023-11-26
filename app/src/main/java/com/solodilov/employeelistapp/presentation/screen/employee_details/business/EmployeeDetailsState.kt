package com.solodilov.employeelistapp.presentation.screen.employee_details.business

import android.os.Parcelable
import com.solodilov.employeelistapp.domain.entity.Employee
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeDetailsState(
    val employeeId: String,
    val employee: Employee? = null,
    val isLoading: Boolean = true,
) : Parcelable
