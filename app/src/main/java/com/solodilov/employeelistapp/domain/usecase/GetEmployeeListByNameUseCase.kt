package com.solodilov.employeelistapp.domain.usecase

import com.solodilov.employeelistapp.domain.entity.Employee
import com.solodilov.employeelistapp.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow

class GetEmployeeListByNameUseCase(private val repository: EmployeeRepository) {

    operator fun invoke(searchQuery: String, forceRefresh: Boolean): Flow<List<Employee>> =
        repository.getEmployeesByName(searchQuery, forceRefresh)
}