package com.temi.ark.views.components.commons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun CardComponent(
    onClick: () -> Unit,
    content: @Composable() () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .padding(5.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(30.dp),
            )
            .height(40.dp)
            .width(200.dp)
            .offset(0.dp, (-5).dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            content()
        }
    }
}
