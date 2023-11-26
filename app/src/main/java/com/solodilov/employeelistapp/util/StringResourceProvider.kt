package com.solodilov.employeelistapp.util

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

class StringResourceProvider(private val context: Context) {

    fun getString(@StringRes resourceId: Int, vararg arguments: Any): String {
        return context.getString(resourceId, *arguments)
    }

    fun getPluralString(@PluralsRes resourceId: Int, quantity: Int, vararg arguments: Any): String {
        return context.resources.getQuantityString(resourceId, quantity, *arguments)
    }
}