package com.temi.ark.views.components.layouts

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.temi.ark.R

@Composable
fun NavBarComponent(
    navController: NavController,
    context: Context? = null,
    onBack: () -> Unit?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.light_green_teal))
            .padding(6.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            navController.navigateUp()
            onBack()
        }) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
            )
        }
    }
}