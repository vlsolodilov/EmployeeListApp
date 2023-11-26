package com.solodilov.employeelistapp.presentation.screen.employee_list.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.solodilov.employeelistapp.R
import com.solodilov.employeelistapp.presentation.ui.theme.TextPrimary
import com.solodilov.employeelistapp.presentation.ui.theme.TextTetriary

@Composable
fun EmptyEmployeesSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(id = R.drawable.ic_magnifying_glass),
            contentDescription = "search",
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.employees_not_found),
            style = MaterialTheme.typography.h3,
            color = TextPrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.try_edit_request),
            style = MaterialTheme.typography.body1,
            color = TextTetriary,
            textAlign = TextAlign.Center,
        )
    }
}