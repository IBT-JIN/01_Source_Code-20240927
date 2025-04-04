package com.temi.ark.views.components.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.temi.ark.views.components.commons.CardComponent
import com.temi.ark.views.components.commons.TextViewComponent

@Composable
fun RecommendationComponent(
    items: List<String>,
    onItemClick: (item: String) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items.forEach { item ->
            item {
                CardComponent(
                    onClick = { onItemClick(item) }
                ) {
                    TextViewComponent(text = item)
                }
            }
        }
    }
}
