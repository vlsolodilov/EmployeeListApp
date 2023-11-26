package com.solodilov.employeelistapp

import android.app.Application
import com.solodilov.employeelistapp.di.dataModule
import com.solodilov.employeelistapp.di.networkModule
import com.solodilov.employeelistapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(listOf(
                dataModule,
                networkModule,
                viewModelModule,
            ))
        }
    }
}