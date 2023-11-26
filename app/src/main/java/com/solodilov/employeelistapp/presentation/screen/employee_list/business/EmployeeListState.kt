package com.solodilov.employeelistapp.presentation.screen.employee_list.business

import android.os.Parcelable
import com.solodilov.employeelistapp.domain.entity.Department
import com.solodilov.employeelistapp.presentation.ui.theme.snackbarColorPurple
import com.solodilov.employeelistapp.util.annotation.EmployeeListOrder
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeListState(
    val employees: List<EmployeeItem> = emptyList(),
    val searchPattern: String = "",
    @EmployeeListOrder val selectedOrder: Int = EmployeeListOrder.BY_ALPHABET,
    val departments: Map<Department, String> = emptyMap(),
    val department: Department = Department.ALL,
    val isLoading: Boolean = true,
    val isCriticalError: Boolean = false,
    val isRefreshing: Boolean = false,
    val snackbarMessage: String = "",
    val snackbarColor: Long = snackbarColorPurple,
) : Parcelable
