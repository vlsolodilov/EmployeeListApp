package com.solodilov.employeelistapp.util.navigation

import androidx.navigation.NavHostController

data class Actions(val navController: NavHostController) {

    val navigateTo: (String) -> Unit = { route ->
        if (!navController.popBackStack(route = route, inclusive = false)) {
            navController.navigate(route)
        }
    }

    val navigateWithArgument: (String, String) -> Unit = { destination, value ->
        val route = "$destination/$value"
        if (!navController.popBackStack(route = route, inclusive = false)) {
            navController.navigate(route)
        }
    }
}