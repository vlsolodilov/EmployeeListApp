package com.solodilov.employeelistapp.data

import com.solodilov.employeelistapp.data.database.EmployeeDao
import com.solodilov.employeelistapp.data.database.entity.toEmployee
import com.solodilov.employeelistapp.data.network.EmployeeApi
import com.solodilov.employeelistapp.data.network.model.toEmployee
import com.solodilov.employeelistapp.data.network.model.toEmployeeDb
import com.solodilov.employeelistapp.domain.entity.Employee
import com.solodilov.employeelistapp.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EmployeeRepositoryImpl(
    private val api: EmployeeApi,
    private val dao: EmployeeDao,
) : EmployeeRepository {

    override fun getEmployees(forceRefresh: Boolean): Flow<List<Employee>> =
        flow { emit(loadEmployees(forceRefresh)) }

    override fun getEmployeesByName(searchQuery: String, forceRefresh: Boolean): Flow<List<Employee>> =
        flow {
            if (forceRefresh) refreshAndCache()
            val employees = dao.getEmployeesByName(searchQuery).map { it.toEmployee() }
            emit(employees)
        }

    override fun getEmployeeById(id: String): Flow<Employee?> =
        flow { emit(dao.getEmployeeById(id)?.toEmployee()) }

    private suspend fun loadEmployees(forceRefresh: Boolean): List<Employee> {
        return if (forceRefresh) {
            refreshAndCache()
        } else {
            dao.getEmployees().map{ it.toEmployee() }.also {
                if (it.isEmpty()) {
                    return refreshAndCache()
                }
            }
        }
    }

    private suspend fun refreshAndCache(): List<Employee> {
        val employees = api.getEmployees().items.mapIndexed { index, employee ->
            employee.copy(avatarUrl = "https://i.pravatar.cc/150?img=${(index + 1) % 70}")
        }
        if (employees.isNotEmpty()) dao.insertEmployees(employees.map { it.toEmployeeDb() })
        return employees.map { it.toEmployee() }
    }
}