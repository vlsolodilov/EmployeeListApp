package com.solodilov.employeelistapp.util.annotation

import androidx.annotation.IntDef
import com.solodilov.employeelistapp.util.annotation.EmployeeListOrder.Companion.BY_ALPHABET
import com.solodilov.employeelistapp.util.annotation.EmployeeListOrder.Companion.BY_BIRTHDAY

@IntDef(BY_ALPHABET, BY_BIRTHDAY)
@Retention(AnnotationRetention.SOURCE)
annotation class EmployeeListOrder {
    companion object {
        const val BY_ALPHABET = 0
        const val BY_BIRTHDAY = 1
    }
}
