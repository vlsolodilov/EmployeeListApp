package com.solodilov.employeelistapp.presentation.screen.employee_list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.solodilov.employeelistapp.R
import com.solodilov.employeelistapp.presentation.screen.employee_list.business.EmployeeItem
import com.solodilov.employeelistapp.presentation.ui.theme.TextPrimary
import com.solodilov.employeelistapp.presentation.ui.theme.TextSecondary
import com.solodilov.employeelistapp.presentation.ui.theme.TextTetriary

@Composable
fun EmployeeItem(
    modifier: Modifier = Modifier,
    employee: EmployeeItem.EmployeeUi,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick(employee.id) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(employee.avatarUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_placeholder),
            contentDescription = stringResource(R.string.employee_avatar),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(72.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = modifier) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = employee.fullName,
                    color = TextPrimary,
                    style = MaterialTheme.typography.h1,
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = employee.userTag.lowercase(),
                    color = TextTetriary,
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Text(text = employee.position,
                color = TextSecondary,
                style = MaterialTheme.typography.body2
            )
        }
    }
}