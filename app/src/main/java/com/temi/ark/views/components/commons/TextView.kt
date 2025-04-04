package com.temi.ark.views.components.commons

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.temi.ark.R

val notoSansKrFontFamily = FontFamily(
    Font(R.font.noto_sans_kr_thin, FontWeight.Thin),
    Font(R.font.noto_sans_kr_regular, FontWeight.Normal),
    Font(R.font.noto_sans_kr_bold, FontWeight.Bold)
)

@Composable
fun TextViewUnitComponent(
    fontSize: Int
): TextUnit {
    val density = LocalDensity.current
    val fixedFontSize = with(density) { fontSize.sp.toPx() }

    return TextUnit(fixedFontSize, TextUnitType.Sp)
}

@Composable
fun TextViewComponent(
    text: String,
    color: Color = colorResource(R.color.black),
    textAlign: TextAlign = TextAlign.Left,
    style: TextStyle = TextStyle(
        fontFamily = notoSansKrFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = TextViewUnitComponent(16)
    )
) {
    Text(
        text = text,
        style = style,
        color = color,
        textAlign = textAlign,
    )
}
