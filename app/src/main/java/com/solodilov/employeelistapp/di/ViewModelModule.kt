package com.solodilov.employeelistapp.di

import com.solodilov.employeelistapp.presentation.screen.employee_details.business.EmployeeDetailsViewModel
import com.solodilov.employeelistapp.presentation.screen.employee_list.business.EmployeeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        EmployeeListViewModel(
            savedStateHandle = get(),
            getEmployeeListUseCase = get(),
            getEmployeeListByNameUseCase = get(),
            provider = get(),
        )
    }

    viewModel { (employeeId: String) ->
        EmployeeDetailsViewModel(
            employeeId = employeeId,
            savedStateHandle = get(),
            getEmployeeUseCase = get(),
            provider = get(),
        )
    }
}