package com.solodilov.employeelistapp.presentation.screen.employee_details.business

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.solodilov.employeelistapp.R
import com.solodilov.employeelistapp.domain.usecase.GetEmployeeUseCase
import com.solodilov.employeelistapp.util.*
import com.solodilov.employeelistapp.util.navigation.Routes
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.syntax.simple.repeatOnSubscription
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import java.time.Period

class EmployeeDetailsViewModel(
    employeeId: String,
    savedStateHandle: SavedStateHandle,
    private val getEmployeeUseCase: GetEmployeeUseCase,
    private val provider: StringResourceProvider,
) : ViewModel(), ContainerHost<EmployeeDetailsState, BasicSideEffect> {

    override val container = container<EmployeeDetailsState, BasicSideEffect>(
        savedStateHandle = savedStateHandle,
        initialState = EmployeeDetailsState(employeeId = employeeId)
    ) {
        initializeState()
    }

    private fun initializeState() = intent {
        repeatOnSubscription {
            getEmployeeDetails()
        }
    }

    private fun getEmployeeDetails() = intent {
        getEmployeeUseCase(state.employeeId).asResult()
            .collect { result ->
                when (result) {
                    is Resource.Loading -> reduce { state.copy(isLoading = true) }
                    is Resource.Success -> reduce {
                        state.copy(
                            isLoading = false,
                            employee = result.data,
                        )
                    }
                    is Resource.Error -> {
                        postSideEffect(NavigateTo(Routes.EmployeeList))
                    }
                }
            }
    }

    fun getEmployeeAge(birthday: LocalDate?): String {
        if (birthday == null) return ""
        val age = Period.between(birthday, LocalDate.now()).years
        return provider.getPluralString(R.plurals.age_amount, age, age)
    }

    fun getEmployeePhone(phone: String?): String {
        if (phone.isNullOrBlank()) return ""
        val phoneDigits = phone.replace(Regex("\\D"), "")
        if (phoneDigits.length != 10) return ""
        return "+7 (${phoneDigits.substring(0, 3)}) ${phoneDigits.substring(3, 6)} ${phoneDigits.substring(6, 8)} ${phoneDigits.substring(8)}"
    }

    fun onBackClick() = intent {
        postSideEffect(NavigateTo(Routes.EmployeeList))
    }
}