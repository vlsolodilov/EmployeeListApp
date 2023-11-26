package com.solodilov.employeelistapp.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

private val RadioButtonSize = 20.dp
private val RadioStrokeWidth = 2.dp
private val RadioSelectedStrokeWidth = 6.dp

@Composable
fun CircleRadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    color: Color
) {
    val thickness = if (selected) RadioSelectedStrokeWidth else RadioStrokeWidth
    val strokeWidth = with(LocalDensity.current) { thickness.toPx() }
    val radius = with(LocalDensity.current) { RadioButtonSize.toPx() } / 2

    Canvas(modifier = modifier.size(RadioButtonSize)) {
        drawCircle(
            color = color,
            radius = radius - strokeWidth / 2,
            style = Stroke(width = strokeWidth)
        )
    }
}