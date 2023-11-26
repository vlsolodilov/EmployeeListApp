package com.solodilov.employeelistapp.presentation.screen.employee_list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.solodilov.employeelistapp.presentation.component.BasicSnackbar
import com.solodilov.employeelistapp.presentation.component.SearchBar
import com.solodilov.employeelistapp.presentation.screen.employee_list.business.EmployeeListViewModel
import com.solodilov.employeelistapp.presentation.ui.theme.BgPrimary
import com.solodilov.employeelistapp.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.viewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmployeeListScreen(navigateWithArgument: (String, String) -> Unit) {

    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }

    val viewModel: EmployeeListViewModel by viewModel()
    val state = viewModel.collectAsState().value
    viewModel.collectSideEffect { sideEffect ->
        handleSideEffect(
            scope = scope,
            snackbarHostState = snackbarHostState,
            sideEffect = sideEffect,
            navigateWithArgument = navigateWithArgument,
        )
    }
    if (state.isCriticalError) {
        ErrorScreen(
            modifier = Modifier.fillMaxSize(),
            onRetryClick = { viewModel.getEmployees(forceRefresh = true) }
        )
    } else {
        val sheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
        )
        Scaffold(
            scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState.value),
            snackbarHost = { SnackbarHost(it) { data -> BasicSnackbar(data, state.snackbarColor) } }
        ) { scaffoldPadding ->
            ModalBottomSheetLayout(
                modifier = Modifier.padding(scaffoldPadding),
                sheetState = sheetState,
                sheetContent = {
                    SortingBottomSheet(
                        selectedOrder = state.selectedOrder,
                        onOrderClick = viewModel::onOrderChange,
                    )
                },
                sheetShape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = BgPrimary)
                        .padding(16.dp)
                ) {
                    Spacer(Modifier.height(16.dp))
                    SearchBar(
                        pattern = state.searchPattern,
                        onPatternChange = viewModel::onSearchPatternChange,
                        onSortClick = { scope.launch { sheetState.show() } },
                        focusManager = focusManager
                    )
                    if (state.departments.isNotEmpty()) {
                        TabSection(
                            selectedDepartment = state.department,
                            departments = state.departments,
                            onDepartmentSelected = viewModel::onDepartmentClick
                        )
                    }

                    when {
                        state.isLoading -> LoadingEmployeesSection()
                        state.employees.isEmpty() -> EmptyEmployeesSection(
                            modifier = Modifier.weight(
                                1f
                            )
                        )
                        else -> SwipeRefresh(
                            state = rememberSwipeRefreshState(state.isRefreshing),
                            onRefresh = {
                                viewModel.refreshEmployees()
                                scope.launch {
                                    snackbarHostState.value.currentSnackbarData?.dismiss()
                                    snackbarHostState.value.showSnackbar("")
                                }
                            }
                        ) {
                            EmployeesSection(
                                modifier = Modifier.weight(1f),
                                employees = state.employees,
                                onEmployeeClick = viewModel::onEmployeeClick,
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun handleSideEffect(
    scope: CoroutineScope,
    snackbarHostState: MutableState<SnackbarHostState>,
    sideEffect: EmployeeListSideEffect,
    navigateWithArgument: (String, String) -> Unit,
) {
    when (sideEffect) {
        is NavigateWithArgument -> navigateWithArgument(sideEffect.destination, sideEffect.argument)
        is ShowMessage -> scope.launch {
            snackbarHostState.value.currentSnackbarData?.dismiss()
            snackbarHostState.value.showSnackbar(sideEffect.message)
        }
        HideMessage -> scope.launch { snackbarHostState.value.currentSnackbarData?.dismiss() }
    }
}