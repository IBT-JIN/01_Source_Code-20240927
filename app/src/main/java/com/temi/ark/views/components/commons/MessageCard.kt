package com.temi.ark.views.components.commons

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.temi.ark.R
import com.temi.ark.models.datas.ChatResponseDataModel
import com.temi.ark.presenters.components.commons.MessageCardComponentPresenter
import com.temi.ark.views.components.layouts.BookContentComponent
import com.temi.ark.views.components.layouts.CarouselComponent
import com.temi.ark.views.components.layouts.ModalComponent

val presenter = MessageCardComponentPresenter()
val model = presenter.model

@Composable
fun MessageCardComponent(
    message: ChatResponseDataModel,
    context: Context? = null,
    navController: NavController? = null,
) {
    LaunchedEffect(model.isLaunch) {
        model.context = context
        model.navController = navController

        presenter.init()
    }

    Column() {
        val cornerRadius = 30.dp
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = if (message.isBot) Arrangement.Start else Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 10.dp)
        ) {
            if (message.isBot) {
                Image(
                    painter = painterResource(id = R.mipmap.bot_ava_foreground),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            val shape = if (message.isBot) {
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = cornerRadius,
                    bottomEnd = cornerRadius,
                    bottomStart = cornerRadius
                )
            } else {
                RoundedCornerShape(
                    topStart = cornerRadius,
                    topEnd = 0.dp,
                    bottomEnd = cornerRadius,
                    bottomStart = cornerRadius
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .widthIn(0.dp, max = 1000.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = shape,
                    )
                    .background(
                        color = colorResource(R.color.white),
                        shape = shape
                    )
                    .padding(horizontal = 24.dp, vertical = 10.dp)
            ) {
                if (message.isTyping) {
                    TypingIndicatorComponent()
                } else {
                    TextViewComponent(
                        text = message.text,
                        color = colorResource(R.color.black),
                        textAlign = TextAlign.Start,
                        style = TextStyle(
                            fontFamily = notoSansKrFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = TextViewUnitComponent(22)
                        )
                    )
                }
            }

            if (!message.isBot) {
                Spacer(modifier = Modifier.width(2.dp))
                Image(
                    painter = painterResource(R.mipmap.user_ava_foreground),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
            }
        }
        if (message.payload != null) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = if (message.isBot) Arrangement.Start else Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                CarouselComponent(
                    items = message.payload,
                    context = context,
                    onItemClick = { item ->
                        presenter.handleSelectBook(item = item)
                    },
                )
            }
        }
        if (model.isShowModal) {
            ModalComponent(onDismissRequest = {}) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    val shape = RoundedCornerShape(30.dp)
                    model.bookSelected?.let { item ->
                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BookContentComponent(item = item, context = context, isShowDetail = true)
                        }
                        Spacer(modifier = Modifier.height(25.dp))
                        if (model.isGoing) {
                            if (context != null) {
                                TextViewComponent(
                                    text = context.getString(R.string.going_to),
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        fontFamily = notoSansKrFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = TextViewUnitComponent(24)
                                    )
                                )
                                Row(modifier = Modifier.padding(vertical = 20.dp)) {
                                    TypingIndicatorComponent()
                                }
                                Button(
                                    onClick = {
                                        presenter.handleReturnHome()
                                    },
                                    Modifier
                                        .heightIn(min = 27.dp)
                                        .widthIn(min = 106.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.blue_teal)),
                                    shape = shape
                                ) {
                                    if (context != null) {
                                        TextViewComponent(
                                            text = context.getString(R.string.go_home_button),
                                            color = colorResource(R.color.white),
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                fontFamily = notoSansKrFontFamily,
                                                fontWeight = FontWeight.Normal,
                                                fontSize = TextViewUnitComponent(18),
                                            )
                                        )
                                    }
                                }
                            }
                        } else if (model.isArrived) {
                            if (context != null) {
                                TextViewComponent(
                                    text = context.getString(R.string.arrived_to),
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        fontFamily = notoSansKrFontFamily,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = TextViewUnitComponent(24)
                                    )
                                )
                                Row(modifier = Modifier.padding(vertical = 20.dp)) {
                                    TextViewComponent(
                                        text = model.timeCount.toString() + "s",
                                        textAlign = TextAlign.Center,
                                        color = colorResource(R.color.blue_teal),
                                        style = TextStyle(
                                            fontFamily = notoSansKrFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = TextViewUnitComponent(30)
                                        )
                                    )
                                }
                                Button(
                                    onClick = {
                                        presenter.handleReturnHome()
                                    },
                                    Modifier
                                        .heightIn(min = 27.dp)
                                        .widthIn(min = 106.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.blue_teal)),
                                    shape = shape
                                ) {
                                    if (context != null) {
                                        TextViewComponent(
                                            text = context.getString(R.string.go_home_button),
                                            color = colorResource(R.color.white),
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                fontFamily = notoSansKrFontFamily,
                                                fontWeight = FontWeight.Normal,
                                                fontSize = TextViewUnitComponent(18),
                                            )
                                        )
                                    }
                                }
                            }
                        } else {
                            if (context != null) {
                                TextViewComponent(
                                    text = context.getString(R.string.goto_question),
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        fontFamily = notoSansKrFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = TextViewUnitComponent(24)
                                    )
                                )
                            }
                            Row(modifier = Modifier.padding(top = 20.dp)) {
                                Button(
                                    onClick = {
                                        presenter.handleCloseModal()
                                    },
                                    Modifier
                                        .heightIn(min = 27.dp)
                                        .widthIn(min = 106.dp),

                                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.dark_gray)),
                                    shape = shape
                                ) {
                                    if (context != null) {
                                        TextViewComponent(
                                            text = context.getString(R.string.no_button),
                                            color = colorResource(R.color.white),
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                fontFamily = notoSansKrFontFamily,
                                                fontWeight = FontWeight.Normal,
                                                fontSize = TextViewUnitComponent(18),
                                            )
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                Button(
                                    onClick = {
                                        presenter.goTo()
                                    },
                                    Modifier
                                        .heightIn(min = 27.dp)
                                        .widthIn(min = 106.dp),

                                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.blue_teal)),
                                    shape = shape
                                ) {
                                    if (context != null) {
                                        TextViewComponent(
                                            text = context.getString(R.string.yes_button),
                                            color = colorResource(R.color.white),
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                fontFamily = notoSansKrFontFamily,
                                                fontWeight = FontWeight.Normal,
                                                fontSize = TextViewUnitComponent(18),
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
    }
}
