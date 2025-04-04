package com.temi.ark.views.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.temi.ark.R
import kotlinx.coroutines.delay

class SplashScreen: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            render()
        }
    }

    @Composable
    private fun render() {
        LaunchedEffect(key1 = true) {
            delay(1500)
            startActivity(
                Intent(
                    this@SplashScreen, MainScreen::class.java
                )
            )
            finish()
        }
        Box(
            modifier = Modifier.fillMaxSize().background(colorResource(R.color.white)),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(id = R.drawable.temi_logo), contentDescription = null)
        }
    }
}