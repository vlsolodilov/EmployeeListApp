package com.solodilov.employeelistapp.presentation.screen.employee_list.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.solodilov.employeelistapp.domain.entity.Department
import com.solodilov.employeelistapp.presentation.ui.theme.ContentPrimary
import com.solodilov.employeelistapp.presentation.ui.theme.TextPrimary
import com.solodilov.employeelistapp.presentation.ui.theme.TextTetriary

@Composable
fun TabSection(
    modifier: Modifier = Modifier,
    selectedDepartment: Department,
    departments: Map<Department, String>,
    onDepartmentSelected: (Department) -> Unit,
) {
    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = selectedDepartment.ordinal,
        contentColor = ContentPrimary,
        backgroundColor = Color.Transparent,
        edgePadding = 0.dp,
    ) {
        departments.forEach { (department, title) ->
            val isSelected = selectedDepartment == department
            Tab(
                selected = isSelected,
                onClick = { onDepartmentSelected(department) },
                text = {
                    Text(
                        text = title,
                        color = if (isSelected) TextPrimary else TextTetriary,
                        style = if (isSelected) MaterialTheme.typography.h4 else MaterialTheme.typography.subtitle2,
                    )
                }
            )
        }
    }
}