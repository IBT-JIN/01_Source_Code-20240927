package com.temi.ark.presenters.components.layouts

import androidx.compose.foundation.gestures.animateScrollBy
import com.temi.ark.models.views.components.layouts.CarouselComponentViewModel
import kotlinx.coroutines.launch

class CarouselComponentPresenter {
    val model = CarouselComponentViewModel()

    fun handleScrollLeft(scrollEnd: Float) {
        model.coroutineScope?.launch {
            model.scrollState?.animateScrollBy(-scrollEnd)
        }
    }

    fun handleScrollRight(scrollEnd: Float) {
        model.coroutineScope?.launch {
            model.scrollState?.animateScrollBy(scrollEnd)
        }
    }
}