package com.solodilov.employeelistapp.util.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.solodilov.employeelistapp.presentation.screen.employee_details.ui.EmployeeDetailsScreen
import com.solodilov.employeelistapp.presentation.screen.employee_list.ui.EmployeeListScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph() {
    val navController = rememberAnimatedNavController()
    val actions = remember(navController) { Actions(navController) }
    val animationSpec: FiniteAnimationSpec<IntOffset> = tween(400)

    AnimatedNavHost(
        navController = navController,
        startDestination = Routes.EmployeeList
    ) {

        composable(
            route = Routes.EmployeeList,
            enterTransition = {
                when (initialState.destination.route) {
                    Routes.EmployeeDetails -> slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Right,
                        animationSpec
                    )
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Routes.EmployeeDetails -> slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec
                    )
                    else -> null
                }
            }
        ) {
            EmployeeListScreen(navigateWithArgument = actions.navigateWithArgument)
        }

        composable(
            route = Routes.EmployeeDetails,
            arguments = listOf(navArgument(Arguments.EmployeeId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            EmployeeDetailsScreen(
                employeeId = arguments.getString(Arguments.EmployeeId, ""),
                navigateTo = actions.navigateTo,
            )
        }
    }
}