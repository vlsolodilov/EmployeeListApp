package com.solodilov.employeelistapp.presentation.screen.employee_list.business

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.solodilov.employeelistapp.R
import com.solodilov.employeelistapp.domain.entity.Department
import com.solodilov.employeelistapp.domain.entity.Employee
import com.solodilov.employeelistapp.domain.usecase.GetEmployeeListByNameUseCase
import com.solodilov.employeelistapp.domain.usecase.GetEmployeeListUseCase
import com.solodilov.employeelistapp.presentation.ui.theme.snackbarColorPurple
import com.solodilov.employeelistapp.presentation.ui.theme.snackbarColorRed
import com.solodilov.employeelistapp.util.*
import com.solodilov.employeelistapp.util.annotation.EmployeeListOrder
import com.solodilov.employeelistapp.util.navigation.Destinations
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.syntax.simple.repeatOnSubscription
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate

class EmployeeListViewModel(
    savedStateHandle: SavedStateHandle,
    private val getEmployeeListUseCase: GetEmployeeListUseCase,
    private val getEmployeeListByNameUseCase: GetEmployeeListByNameUseCase,
    private val provider: StringResourceProvider,
) : ViewModel(), ContainerHost<EmployeeListState, EmployeeListSideEffect> {

    override val container = container<EmployeeListState, EmployeeListSideEffect>(
        savedStateHandle = savedStateHandle,
        initialState = EmployeeListState()
    ) {
        initializeState()
    }

    private fun initializeState() = intent {
        repeatOnSubscription {
            reduce { state.copy(departments = getDepartmentMap()) }
            getEmployees()
        }
    }

    fun getEmployees(forceRefresh: Boolean = false) = intent {
        val employees = if (state.searchPattern.isBlank()) {
            getEmployeeListUseCase(forceRefresh)
        } else {
            getEmployeeListByNameUseCase(state.searchPattern, false)
        }
        employees.asResult()
            .collect { result ->
                when (result) {
                    is Resource.Loading -> reduce { state.copy(isLoading = true, isCriticalError = false) }
                    is Resource.Success -> reduce {
                        state.copy(
                            isLoading = false,
                            employees = getSortedEmployees(
                                employees = result.data,
                                order = state.selectedOrder,
                                department = state.department
                            ),
                        )
                    }
                    is Resource.Error -> {
                        reduce {
                            state.copy(
                                isLoading = false,
                                isCriticalError = true,
                            )
                        }
                    }
                }
            }
    }

    fun refreshEmployees() = intent {
        val employees = if (state.searchPattern.isBlank()) {
            getEmployeeListUseCase(forceRefresh = true)
        } else {
            getEmployeeListByNameUseCase(state.searchPattern, forceRefresh = true)
        }
        employees.asResult()
            .collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        reduce {
                            state.copy(
                                isRefreshing = true,
//                                snackbarMessage = provider.getString(R.string.load),
                                snackbarColor = snackbarColorPurple,
                            )
                        }
                        postSideEffect(ShowMessage(provider.getString(R.string.load)))
                    }
                    is Resource.Success -> {
                        reduce {
                            state.copy(
                                isRefreshing = false,
                                employees = getSortedEmployees(
                                    employees = result.data,
                                    order = state.selectedOrder,
                                    department = state.department
                                ),
                            )
                        }
                        postSideEffect(HideMessage)
                    }
                    is Resource.Error -> {
                        reduce {
                            state.copy(
                                isRefreshing = false,
                                snackbarColor = snackbarColorRed,
                            )
                        }
                        postSideEffect(ShowMessage(getErrorText(result.errorType)))
                    }
                }
            }
    }

    private fun getErrorText(errorType: ErrorType): String =
        when (errorType) {
            ErrorType.CONNECTION -> provider.getString(R.string.error_connection)
            else -> provider.getString(R.string.error_generic)
        }

    fun onSearchPatternChange(searchPattern: String) = intent {
        reduce { state.copy(searchPattern = searchPattern) }
        getEmployees()
    }

    private fun getSortedEmployees(
        employees: List<Employee>,
        @EmployeeListOrder order: Int,
        department: Department,
    ): List<EmployeeItem> {
        val sortedEmployeesByDepartment =
            employees.filter { department == Department.ALL || it.department == department }
        return when (order) {
            EmployeeListOrder.BY_ALPHABET -> {
                sortedEmployeesByDepartment
                    .map { it.toEmployeeUi() }
                    .sortedWith(EmployeeItem.EmployeeUi.sortByName)
            }
            EmployeeListOrder.BY_BIRTHDAY -> {
                val resultList = mutableListOf<EmployeeItem>()
                val today = LocalDate.now()
                val employeesThisYear = sortedEmployeesByDepartment
                    .filter { it.birthday?.withYear(today.year)?.isAfter(today) ?: true }
                    .sortedWith(compareBy({ it.birthday?.monthValue }, { it.birthday?.dayOfMonth }))
                    .map { it.toEmployeeUi() }
                val employeesNextYear = sortedEmployeesByDepartment
                    .filter { it.birthday?.withYear(today.year)?.isBefore(today) ?: false }
                    .sortedWith(compareBy({ it.birthday?.monthValue }, { it.birthday?.dayOfMonth }))
                    .map { it.toEmployeeUi() }
                resultList.addAll(employeesThisYear)
                if (employeesNextYear.isNotEmpty()) {
                    resultList.add(EmployeeItem.YearDivider(today.year.plus(1).toString()))
                    resultList.addAll(employeesNextYear)
                }
                resultList
            }
            else -> emptyList()
        }
    }

    fun onDepartmentClick(department: Department) = intent {
        reduce { state.copy(department = department) }
        getEmployees()
    }

    private fun getDepartmentMap(): Map<Department, String> =
        Department.values().associateWith(::getDepartmentText)

    private fun getDepartmentText(department: Department): String =
        when (department) {
            Department.ALL -> provider.getString(R.string.all)
            Department.ANDROID -> provider.getString(R.string.android)
            Department.IOS -> provider.getString(R.string.ios)
            Department.DESIGN -> provider.getString(R.string.design)
            Department.MANAGEMENT -> provider.getString(R.string.management)
            Department.QA -> provider.getString(R.string.qa)
            Department.BACK_OFFICE -> provider.getString(R.string.back_office)
            Department.FRONTEND -> provider.getString(R.string.frontend)
            Department.HR -> provider.getString(R.string.hr)
            Department.PR -> provider.getString(R.string.pr)
            Department.BACKEND -> provider.getString(R.string.backend)
            Department.SUPPORT -> provider.getString(R.string.support)
            Department.ANALYTICS -> provider.getString(R.string.analytics)
        }

    fun onOrderChange(@EmployeeListOrder order: Int) = intent {
        reduce { state.copy(selectedOrder = order) }
        getEmployees()
    }

    fun onEmployeeClick(id: String) = intent {
        postSideEffect(NavigateWithArgument(
            destination = Destinations.EmployeeDetailsScreen,
            argument = id,
        ))
    }
}