package com.solodilov.employeelistapp.domain.usecase

import com.solodilov.employeelistapp.domain.entity.Employee
import com.solodilov.employeelistapp.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow

class GetEmployeeListUseCase (private val repository: EmployeeRepository) {

    operator fun invoke(forceRefresh: Boolean): Flow<List<Employee>> =
        repository.getEmployees(forceRefresh)
}