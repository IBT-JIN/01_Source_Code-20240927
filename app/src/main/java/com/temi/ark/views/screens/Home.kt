package com.temi.ark.views.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.temi.ark.R
import com.temi.ark.views.components.commons.TextViewComponent
import com.temi.ark.views.components.commons.TextViewUnitComponent
import com.temi.ark.views.components.commons.notoSansKrFontFamily

@Composable
fun HomeScreen(navController: NavController, context: Context) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(colorResource(R.color.white), colorResource(R.color.green_teal))
                )
            )
            .padding(top = 50.dp),
    ) {
        Column {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row (
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                ) {
                    TextViewComponent(
                        text = context.getString(R.string.hello_there),
                        color = colorResource(R.color.blue_gray),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = notoSansKrFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = TextViewUnitComponent(40)
                        )
                    )
                }
            }
            Row (
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(
                        start = 40.dp
                    )
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                        ),
                        onClick = {
                            navController.navigate(
                                context.getString(R.string.router_chat) + "/ "
                            )
                        }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.mic),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.soundwave_combine),
                        contentDescription = null,
                    )
                }

                Column {
                    Column (
                        modifier = Modifier
                            .padding(
                                start = 200.dp, end = 200.dp
                            )
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Box(
                            modifier = Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(30.dp)
                                )
                                .clip(
                                    RoundedCornerShape(30.dp)
                                )
                                .background(
                                    colorResource(R.color.medium_sea_green)
                                )
                                .padding(10.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            TextViewComponent(
                                text = context.getString(R.string.some_recommendation_questions),
                                color = colorResource(R.color.white),
                                style = TextStyle(
                                    fontFamily = notoSansKrFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = TextViewUnitComponent(30)
                                )
                            )
                        }
                    }
                    val questions = listOf(
                        context.getString(R.string.question_1),
                        context.getString(R.string.question_2),
                        context.getString(R.string.question_3),
                        context.getString(R.string.question_4),
                        context.getString(R.string.question_5),
                    )
                    questions.map { question ->
                        Column (
                            modifier = Modifier
                                .padding(
                                    top = 30.dp
                                )
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.white),
                                ),
                                modifier = Modifier
                                    .shadow(
                                        elevation = 10.dp,
                                        shape = RoundedCornerShape(30.dp)
                                    )
                                    .clip(
                                        RoundedCornerShape(30.dp)
                                    )
                                    .background(
                                        colorResource(R.color.white)
                                    ),
                                onClick = {
                                    navController.navigate(
                                        context.getString(R.string.router_chat) + "/" + question
                                    )
                                }
                            ) {
                                TextViewComponent(
                                    text = question,
                                    color = colorResource(R.color.roman_silver),
                                    style = TextStyle(
                                        fontFamily = notoSansKrFontFamily,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = TextViewUnitComponent(30)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
