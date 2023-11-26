package com.solodilov.employeelistapp.presentation.screen.employee_list.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.solodilov.employeelistapp.presentation.ui.theme.GradientEnd
import com.solodilov.employeelistapp.presentation.ui.theme.GradientStart

@Composable
fun LoadingEmployeesSection(modifier: Modifier = Modifier) {

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(count = 10) {
            ShimmerEmployeeItemAnimation()
        }
    }
}

val ShimmerColor = listOf(
    GradientStart,
    GradientEnd,
)

@Composable
fun ShimmerEmployeeItem(
    modifier: Modifier = Modifier,
    brush: Brush,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(
            modifier = Modifier
                .clip(CircleShape)
                .size(72.dp)
                .background(brush)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = modifier) {
            Spacer(
                modifier = Modifier
                    .width(144.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(brush)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Spacer(
                modifier = Modifier
                    .width(80.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(brush = brush)
            )
        }
    }
}

@Composable
fun ShimmerEmployeeItemAnimation(
) {

    /*
    Create InfiniteTransition
    which holds child animation like [Transition]
    animations start running as soon as they enter
    the composition and do not stop unless they are removed
    */
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        /*
        Specify animation positions,
        initial Values 0F means it
        starts from 0 position
        */
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(


            // Tween Animates between values over specified [durationMillis]
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    /*
    Create a gradient using the list of colors
    Use Linear Gradient for animating in any direction according to requirement
    start=specifies the position to start with in cartesian like system Offset(10f,10f) means x(10,0) , y(0,10)
    end = Animate the end position to give the shimmer effect using the transition created above
    */
    val brush = Brush.linearGradient(
        colors = ShimmerColor,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerEmployeeItem(brush = brush)
}

