package com.temi.ark.models.views.components.commons

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.temi.ark.models.datas.ARKPayloadResponseDataModel

class MessageCardComponentViewModel: ViewModel() {
    var isLaunch by mutableStateOf(true)
    var isShowModal by mutableStateOf(false)
    var isGoing by mutableStateOf(false)
    var isArrived by mutableStateOf(false)
    var isReturned by mutableStateOf(false)
    var bookSelected by mutableStateOf<ARKPayloadResponseDataModel?>(null)
    var timeCount by mutableStateOf(60)
    var context: Context? = null
    var navController: NavController? = null;

    fun setIsShowModal(data: Boolean) {
        isShowModal = data
    }

    fun setIsGoing(data: Boolean) {
        isGoing = data
    }

    fun setIsArrived(data: Boolean) {
        isArrived = data
    }

    fun setIsReturned(data: Boolean) {
        isReturned = data
    }

    fun setBook(data: ARKPayloadResponseDataModel?) {
        bookSelected = data
    }

    fun setCount(data: Int) {
        timeCount = data
    }
}