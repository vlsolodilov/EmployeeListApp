package com.solodilov.employeelistapp.presentation.screen.employee_list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.solodilov.employeelistapp.presentation.screen.employee_list.business.EmployeeItem
import com.solodilov.employeelistapp.presentation.ui.theme.ContentSecondary

@Composable
fun EmployeesSection(
    modifier: Modifier = Modifier,
    employees: List<EmployeeItem>,
    onEmployeeClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = employees) { item ->
            when (item) {
                is EmployeeItem.EmployeeUi -> EmployeeItem(
                    employee = item,
                    onClick = { onEmployeeClick(item.id) }
                )
                is EmployeeItem.YearDivider -> YearDivider(year = item.year)
            }
        }
    }
}

@Composable
fun YearDivider(
    modifier: Modifier = Modifier,
    year: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(68.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .background(ContentSecondary)
                .weight(1f)
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = year,
            style = MaterialTheme.typography.subtitle2,
            color = ContentSecondary,
        )
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .background(ContentSecondary)
                .weight(1f)
        )
    }
}