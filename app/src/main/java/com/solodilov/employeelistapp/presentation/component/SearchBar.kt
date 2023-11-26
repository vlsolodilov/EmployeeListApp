package com.solodilov.employeelistapp.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.solodilov.employeelistapp.R
import com.solodilov.employeelistapp.presentation.ui.theme.BgSecondary
import com.solodilov.employeelistapp.presentation.ui.theme.ContentPrimary
import com.solodilov.employeelistapp.presentation.ui.theme.ContentSecondary
import com.solodilov.employeelistapp.presentation.ui.theme.TextPrimary

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    pattern: String,
    onPatternChange: (String) -> Unit,
    onSortClick: () -> Unit,
    focusManager: FocusManager?,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier
                .weight(1f)
                .heightIn(40.dp),
            value = pattern,
            onValueChange = { onPatternChange(it) },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search),
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Transparent,
                disabledIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
                errorIndicatorColor = Transparent,
                backgroundColor = BgSecondary,
                textColor = TextPrimary,
            ),
            textStyle = MaterialTheme.typography.subtitle2,
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(
                    text = stringResource(R.string.placeholder_search),
                    color = ContentSecondary,
                    style = MaterialTheme.typography.subtitle2,
                )
            },
            trailingIcon = {
                if (pattern.isBlank()) Icon(
                    modifier = Modifier.clickable { onSortClick() },
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = stringResource(R.string.sort),
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager?.clearFocus() }),
        )
        if (pattern.isNotBlank()) {
            Text(
                modifier = modifier
                    .clickable { onPatternChange("") }
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 12.dp),
                text = stringResource(R.string.cancel),
                color = ContentPrimary,
                style = MaterialTheme.typography.h2,
            )
        }
    }
}