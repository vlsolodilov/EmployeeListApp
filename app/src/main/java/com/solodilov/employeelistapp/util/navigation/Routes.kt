package com.solodilov.employeelistapp.util.navigation

object Routes {
    const val EmployeeList = "employee_list"
    const val EmployeeDetails = "${Destinations.EmployeeDetailsScreen}/{${Arguments.EmployeeId}}"
}

object Arguments {
    const val EmployeeId = "employee_id"
}

object Destinations {
    const val EmployeeDetailsScreen = "employee_details_screen"
}