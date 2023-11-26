package com.solodilov.employeelistapp.data.database

import androidx.room.*
import com.solodilov.employeelistapp.data.database.entity.EmployeeDb

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployees(employees: List<EmployeeDb>)

    @Query("SELECT * FROM employee")
    fun getEmployees(): List<EmployeeDb>

    @Query("SELECT * FROM employee WHERE firstName LIKE '%' || :searchQuery || '%'" +
            " OR lastName LIKE '%' || :searchQuery || '%'")
    fun getEmployeesByName(searchQuery: String): List<EmployeeDb>

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getEmployeeById(id: String): EmployeeDb?
}
