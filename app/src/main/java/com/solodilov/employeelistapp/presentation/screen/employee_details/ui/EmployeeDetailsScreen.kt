package com.solodilov.employeelistapp.presentation.screen.employee_details.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.solodilov.employeelistapp.R
import com.solodilov.employeelistapp.presentation.screen.employee_details.business.EmployeeDetailsViewModel
import com.solodilov.employeelistapp.presentation.ui.theme.BgSecondary
import com.solodilov.employeelistapp.presentation.ui.theme.TextPrimary
import com.solodilov.employeelistapp.presentation.ui.theme.TextSecondary
import com.solodilov.employeelistapp.presentation.ui.theme.TextTetriary
import com.solodilov.employeelistapp.util.BasicSideEffect
import com.solodilov.employeelistapp.util.NavigateTo
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.time.format.DateTimeFormatter


@Composable
fun EmployeeDetailsScreen(
    employeeId: String,
    navigateTo: (String) -> Unit,
) {

    val context = LocalContext.current

    val viewModel: EmployeeDetailsViewModel by viewModel { parametersOf(employeeId) }
    val state = viewModel.collectAsState().value
    viewModel.collectSideEffect { sideEffect ->
        handleSideEffect(
            sideEffect = sideEffect,
            onNavigateTo = navigateTo,
        )
    }

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(color = BgSecondary),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                IconButton(
                    onClick = viewModel::onBackClick,
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back"
                    )
                }
            }
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(state.employee?.avatarUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_placeholder),
                contentDescription = stringResource(R.string.employee_avatar),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(104.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${state.employee?.lastName ?: ""} ${state.employee?.firstName ?: ""}",
                    color = TextPrimary,
                    style = MaterialTheme.typography.h6,
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = state.employee?.userTag?.lowercase() ?: "",
                    color = TextTetriary,
                    style = MaterialTheme.typography.h3,
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = state.employee?.position ?: "",
                color = TextSecondary,
                style = MaterialTheme.typography.body2,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    modifier = Modifier.weight(0.5f),
                    text = state.employee?.birthday?.format(DateTimeFormatter.ofPattern("d MMMM yyyy"))
                        ?: "",
                    style = MaterialTheme.typography.h1
                )
                Text(
                    modifier = Modifier.weight(0.5f),
                    textAlign = TextAlign.End,
                    text = viewModel.getEmployeeAge(state.employee?.birthday),
                    color = TextTetriary,
                    style = MaterialTheme.typography.h1,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    modifier = Modifier.weight(0.5f),
                    text = viewModel.getEmployeePhone(state.employee?.phone),
                    style = MaterialTheme.typography.h1
                )
            }
        }
    }
    BackHandler(onBack = viewModel::onBackClick)
}

private fun handleSideEffect(
    sideEffect: BasicSideEffect,
    onNavigateTo: (String) -> Unit,
) {
    when (sideEffect) {
        is NavigateTo -> onNavigateTo(sideEffect.route)
    }
}