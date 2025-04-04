package com.temi.ark.models.views.components.layouts

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.temi.ark.models.datas.ARKPayloadResponseDataModel
import kotlinx.coroutines.CoroutineScope

class CarouselComponentViewModel {
    var isLaunch by mutableStateOf(true)
    var items: List<ARKPayloadResponseDataModel>? = null
    var scrollState: ScrollState? = null
    var coroutineScope: CoroutineScope? = null
}