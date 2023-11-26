package com.solodilov.employeelistapp.domain.repository

import com.solodilov.employeelistapp.domain.entity.Employee
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    fun getEmployees(forceRefresh: Boolean): Flow<List<Employee>>
    fun getEmployeesByName(searchQuery: String, forceRefresh: Boolean): Flow<List<Employee>>
    fun getEmployeeById(id: String): Flow<Employee?>
}