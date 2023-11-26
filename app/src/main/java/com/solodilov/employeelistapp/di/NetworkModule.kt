package com.solodilov.employeelistapp.di

import com.solodilov.employeelistapp.data.network.EmployeeApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://stoplight.io/mocks/kode-education/trainee-test/25143926/"

val networkModule = module {

    single { okHttp() }

    single { gsonConverterFactory() }

    single { retrofit(get(), get()) }

    single { get<Retrofit>().create(EmployeeApi::class.java) }

}

private fun gsonConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()

private fun okHttp(): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor())
        .build()

private fun retrofit(
    gsonConverterFactory: GsonConverterFactory,
    okHttpClient: OkHttpClient,
) = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(gsonConverterFactory)
    .client(okHttpClient)
    .build()

private fun loggingInterceptor() =
    HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BASIC)