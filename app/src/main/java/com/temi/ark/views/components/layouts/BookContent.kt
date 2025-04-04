package com.temi.ark.views.components.layouts

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.temi.ark.R
import com.temi.ark.models.datas.ARKPayloadResponseDataModel
import coil.compose.AsyncImage
import com.temi.ark.views.components.commons.TextViewComponent
import com.temi.ark.views.components.commons.TextViewUnitComponent
import com.temi.ark.views.components.commons.notoSansKrFontFamily

@Composable
fun BookContentComponent(
    item: ARKPayloadResponseDataModel,
    isShowDetail: Boolean = false,
    context: Context? = null
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        AsyncImage(
            modifier = Modifier.clip(RoundedCornerShape(10.dp)).height(200.dp),
            model = item.image,
            contentDescription = null,
            error = painterResource(R.drawable.placeholder)
        )
        TextViewComponent(
            text = item.title,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontFamily = notoSansKrFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = TextViewUnitComponent(20),
            )
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.75.dp,
            color = colorResource(R.color.black)
        )
        if (context != null) {
            TextViewComponent(
                text = context.getString(R.string.author) +" "+ item.author,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = notoSansKrFontFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = TextViewUnitComponent(16)
                )
            )
            TextViewComponent(
                text = context.getString(R.string.call_no) +" "+ item.call_no,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = notoSansKrFontFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = TextViewUnitComponent(16)
                )
            )
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                TextViewComponent(
                    text = context.getString(R.string.loanable),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontFamily = notoSansKrFontFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = TextViewUnitComponent(16)
                    )
                )
                TextViewComponent(
                    text = if (item.loan_able == "Y") {
                        context.getString(R.string.carousel_loanable) ?: ""
                    } else {
                        context.getString(R.string.carousel_not_loanable) ?: ""
                    },
                    textAlign = TextAlign.Center,
                    color = if (item.loan_able == "Y") {
                        colorResource(R.color.blue_teal)
                    } else {
                        colorResource(R.color.red)
                    },
                    style = TextStyle(
                        fontFamily = notoSansKrFontFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = TextViewUnitComponent(16)
                    )
                )
            }
            if (isShowDetail) {
                TextViewComponent(
                    text = context.getString(R.string.reg_no) +" "+ item.reg_no,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontFamily = notoSansKrFontFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = TextViewUnitComponent(16)
                    )
                )
                TextViewComponent(
                    text = context.getString(R.string.category) +" "+ (item.category ?: ""),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontFamily = notoSansKrFontFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = TextViewUnitComponent(16)
                    )
                )
            }
        }
    }
}
