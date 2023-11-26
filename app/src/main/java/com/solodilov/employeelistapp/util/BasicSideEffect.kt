package com.solodilov.employeelistapp.util

sealed interface BasicSideEffect
sealed interface ArgumentsSideEffect
sealed interface EmployeeListSideEffect

data class NavigateTo(val route: String) : BasicSideEffect, ArgumentsSideEffect
data class NavigateWithArgument(val destination: String, val argument: String) : ArgumentsSideEffect, EmployeeListSideEffect
data class ShowMessage(val message: String) : EmployeeListSideEffect
object HideMessage : EmployeeListSideEffect