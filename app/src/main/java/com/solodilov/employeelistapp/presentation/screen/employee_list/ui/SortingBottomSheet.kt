package com.solodilov.employeelistapp.presentation.screen.employee_list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.solodilov.employeelistapp.R
import com.solodilov.employeelistapp.presentation.component.CircleRadioButton
import com.solodilov.employeelistapp.presentation.ui.theme.BgPrimary
import com.solodilov.employeelistapp.presentation.ui.theme.ContentPrimary
import com.solodilov.employeelistapp.presentation.ui.theme.ContentSecondary
import com.solodilov.employeelistapp.presentation.ui.theme.TextPrimary
import com.solodilov.employeelistapp.util.annotation.EmployeeListOrder

@Composable
fun SortingBottomSheet(
    modifier: Modifier = Modifier,
    @EmployeeListOrder selectedOrder: Int,
    onOrderClick: (Int) -> Unit,
) {
    Box(
        modifier = modifier
            .height(359.dp)
            .padding(horizontal = 16.dp)
            .background(BgPrimary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .width(56.dp)
                    .height(4.dp)
                    .background(ContentSecondary)
                    .clip(RoundedCornerShape(2.dp)),
                contentAlignment = Alignment.Center
            ) {
                Spacer(modifier = Modifier.fillMaxWidth())
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.sorting),
                style = MaterialTheme.typography.h5,
                color = TextPrimary,
            )
            Spacer(modifier = Modifier.height(16.dp))
            val orderMap = mapOf(
                EmployeeListOrder.BY_ALPHABET to stringResource(id = R.string.by_alphabet),
                EmployeeListOrder.BY_BIRTHDAY to stringResource(id = R.string.by_birthday),
            )
            orderMap.forEach { (order, text) ->
                Row(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .clickable { onOrderClick(order) },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CircleRadioButton(
                        modifier = Modifier.padding(8.dp),
                        selected = order == selectedOrder,
                        color = ContentPrimary,
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.subtitle2,
                        color = TextPrimary,
                    )
                }
            }
        }
    }
}