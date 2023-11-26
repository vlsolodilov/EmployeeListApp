package com.solodilov.employeelistapp.presentation.screen.employee_list.business

import android.os.Parcelable
import com.solodilov.employeelistapp.domain.entity.Department
import com.solodilov.employeelistapp.domain.entity.Employee
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

sealed class EmployeeItem : Parcelable {
    @Parcelize data class EmployeeUi(
        val id: String,
        val avatarUrl: String,
        val fullName: String,
        val userTag: String,
        val position: String,
        val birthday: LocalDate?,
        val department: Department,
    ) : EmployeeItem() {
        companion object {
            val sortByName: Comparator<EmployeeUi> = compareBy { it.fullName }
        }
    }
    @Parcelize data class YearDivider(val year: String) : EmployeeItem()
}

fun Employee.toEmployeeUi() = EmployeeItem.EmployeeUi(
    id = this.id,
    avatarUrl = this.avatarUrl,
    fullName = "${this.lastName} ${this.firstName}",
    userTag = this.userTag,
    position = this.position,
    birthday = this.birthday,
    department = this.department,
)
