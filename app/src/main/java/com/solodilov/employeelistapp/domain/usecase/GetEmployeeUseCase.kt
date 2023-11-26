package com.solodilov.employeelistapp.domain.usecase

import com.solodilov.employeelistapp.domain.entity.Employee
import com.solodilov.employeelistapp.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow

class GetEmployeeUseCase (private val repository: EmployeeRepository) {

    operator fun invoke(id: String): Flow<Employee?> =
        repository.getEmployeeById(id)
}