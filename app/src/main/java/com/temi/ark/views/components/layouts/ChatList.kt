package com.temi.ark.views.components.layouts

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.temi.ark.R
import com.temi.ark.models.datas.ChatResponseDataModel
import com.temi.ark.views.components.commons.MessageCardComponent

@Composable
fun ChatListComponent(
    messages: List<ChatResponseDataModel>,
    modifier: Modifier = Modifier,
    itemSpacerHeight: Dp,
    listState: LazyListState,
    context: Context? = null,
    navController: NavController? = null,
) {
    val colors = listOf(Color.Transparent, colorResource(R.color.white))
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer { alpha = 0.99F }
            .drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(
                        colors,
                        startY = size.height - 70f,
                        endY = size.height
                    ),
                    blendMode = BlendMode.DstOut
                )
            }
    ) {
        items(messages) { message ->
            MessageCardComponent(
                message = message,
                context = context,
                navController = navController,
            )
        }

        item {
            Spacer(modifier = Modifier.height(itemSpacerHeight))
        }
    }
}
