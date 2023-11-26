package com.solodilov.employeelistapp.di

import com.solodilov.employeelistapp.data.EmployeeRepositoryImpl
import com.solodilov.employeelistapp.data.database.EmployeeDatabase
import com.solodilov.employeelistapp.domain.repository.EmployeeRepository
import com.solodilov.employeelistapp.domain.usecase.GetEmployeeListByNameUseCase
import com.solodilov.employeelistapp.domain.usecase.GetEmployeeListUseCase
import com.solodilov.employeelistapp.domain.usecase.GetEmployeeUseCase
import com.solodilov.employeelistapp.util.StringResourceProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    single { EmployeeDatabase.getInstance(androidApplication()) }

    single { get<EmployeeDatabase>().employeeDao() }

    single<EmployeeRepository> { EmployeeRepositoryImpl(get(), get()) }

    factory { GetEmployeeListUseCase(get()) }

    factory { GetEmployeeListByNameUseCase(get()) }

    factory { GetEmployeeUseCase(get()) }

    single { StringResourceProvider(context = get()) }
}