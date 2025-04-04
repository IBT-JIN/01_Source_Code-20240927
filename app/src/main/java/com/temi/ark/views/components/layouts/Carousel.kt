package com.temi.ark.views.components.layouts

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.temi.ark.R
import com.temi.ark.models.datas.ARKPayloadResponseDataModel
import com.temi.ark.presenters.components.layouts.CarouselComponentPresenter
import com.temi.ark.views.components.commons.TextViewComponent
import com.temi.ark.views.components.commons.TextViewUnitComponent
import com.temi.ark.views.components.commons.notoSansKrFontFamily

val presenter = CarouselComponentPresenter()
val model = presenter.model

@Composable
fun CarouselComponent(
    items: List<ARKPayloadResponseDataModel>? = null,
    itemWidth: Dp = 345.dp,
    itemSpacing: Dp = 20.dp,
    onItemClick: (item: ARKPayloadResponseDataModel) -> Unit,
    context: Context? = null
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val density = LocalDensity.current

    LaunchedEffect(model.isLaunch) {
        model.items = items
        model.coroutineScope = coroutineScope
        model.scrollState = scrollState
    }

    if (items?.isNotEmpty() == true) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(horizontal = 75.dp)
                    .horizontalScroll(scrollState),
            ) {
                items?.map { item ->
                    val shape = RoundedCornerShape(30.dp)
                    Box(
                        modifier = Modifier
                            .padding(horizontal = itemSpacing)
                            .width(itemWidth)
                            .shadow(
                                elevation = 10.dp,
                                shape = shape,
                            )
                            .background(
                                color = colorResource(R.color.white),
                                shape = shape
                            )
                            .fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 15.dp, horizontal = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BookContentComponent(item = item, context = context)
                            Spacer(modifier = Modifier.height(60.dp))
                        }
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(bottom = 15.dp)
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                        ) {
                            Button(
                                onClick = {
                                    onItemClick(item)
                                },
                                Modifier
                                    .heightIn(min = 27.dp)
                                    .paddingFromBaseline(bottom = 20.dp)
                                    .widthIn(min = 106.dp),

                                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.blue_teal)),
                                shape = shape
                            ) {
                                if (context != null) {
                                    TextViewComponent(
                                        text = context.getString(R.string.carousel_button),
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


            Image(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .clickable {
                        presenter.handleScrollRight(
                            scrollEnd = with(density) { 1580.dp.toPx() },
                        )
                    }
            )

            Image(
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .clickable {
                        presenter.handleScrollLeft(
                            scrollEnd = with(density) { 1580.dp.toPx() },
                        )
                    }
            )
        }
    }
}