package com.temi.ark.views.components.commons

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.temi.ark.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ButtonComponent(
    onMicClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onKeyboardClick: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    
    Row(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 30.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onDeleteClick,
            modifier = Modifier
                .padding(top = 30.dp)
                .size(120.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_refresh_history),
                contentDescription = null,
                tint = colorResource(R.color.white),
                modifier = Modifier.size(60.dp)
            )
        }

        var micButtonExpanded by remember { mutableStateOf(false) }
        val micButtonSize by animateDpAsState(
            targetValue = if (micButtonExpanded) 140.dp else 120.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        Button(
            onClick = {
                micButtonExpanded = !micButtonExpanded
                onMicClick()
                coroutineScope.launch {
                    delay(2000)
                    micButtonExpanded = !micButtonExpanded
                }
            },
            modifier = Modifier
                .size(micButtonSize)
                .background(Color.Transparent, CircleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.white)
            ),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_mic_chat_screen),
                contentDescription = null,
                tint = colorResource(R.color.dark_gray),
                modifier = Modifier.size(60.dp)
            )
        }
        Button(
            onClick = onKeyboardClick,
            modifier = Modifier
                .padding(top = 30.dp)
                .size(120.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_keyboard),
                contentDescription = null,
                tint = colorResource(R.color.white),
                modifier = Modifier.size(60.dp)
            )
        }
    }
}