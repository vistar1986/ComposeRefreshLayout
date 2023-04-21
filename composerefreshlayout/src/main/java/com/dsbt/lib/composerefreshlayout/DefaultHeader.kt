package com.dsbt.lib.composerefreshlayout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@Composable
fun DefaultRefreshHeader(
    state: DragState.RefreshState,
    color: Color = Color(0xFF808080)
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
    ) {
        val agree =
            if (state.gestureState == GestureState.ReadyForAction || state.gestureState == GestureState.InProgress) {
                90f
            } else {
                -90f
            }
        val rotation by animateFloatAsState(targetValue = agree)
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (state.gestureState == GestureState.InProgress) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(24.dp),
                    color = color,
                    strokeWidth = 2.dp
                )
            } else if (!state.gestureState.isFinishing) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "", modifier = Modifier
                        .padding(end = 12.dp)
                        .size(24.dp)
                        .padding(2.dp)
                        .graphicsLayer {
                            this.rotationZ = rotation
                        },
                    colorFilter = ColorFilter.tint(color = color)
                )
            }
            var headerText by remember {
                mutableStateOf("")
            }
            val text = when (state.gestureState) {
                GestureState.IDLE -> stringResource(id = R.string.header_idle)
                GestureState.ReadyForAction -> stringResource(id = R.string.header_pulling)
                GestureState.InProgress -> stringResource(id = R.string.header_refreshing)
                GestureState.Success -> stringResource(id = R.string.header_complete)
                GestureState.Failed -> stringResource(id = R.string.header_failed)
                else -> ""
            }
            if (headerText != text && text.isNotEmpty()) {
                headerText = text
            }
            Text(text = headerText, color = color)
        }
    }
}