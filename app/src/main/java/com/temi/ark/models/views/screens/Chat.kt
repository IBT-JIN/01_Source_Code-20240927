package com.temi.ark.models.views.screens

import android.content.Context
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.temi.ark.models.datas.ChatResponseDataModel
import kotlinx.coroutines.CoroutineScope

class ChatScreenViewModel: ViewModel() {
    var isKeyboardVisible by mutableStateOf(false)
    var isInputFieldFocused by mutableStateOf(false)
    var textChat by mutableStateOf("")
    var messages by mutableStateOf(listOf<ChatResponseDataModel>())
    var isLaunch by mutableStateOf(true)
    var listState = LazyListState()
    var coroutineScope: CoroutineScope? = null
    var context: Context? = null
    var defaultQuestion: String? = null

    fun setIsKeyboardVisible(data: Boolean) {
        isKeyboardVisible = data
    }

    fun setIsInputFieldFocused(data: Boolean) {
        isInputFieldFocused = data
    }

    fun setChat(data: String) {
        textChat = data
    }

    fun setMessages(data: SnapshotStateList<ChatResponseDataModel>) {
        messages = data
    }

    fun setQuestionDefault(data: String) {
        defaultQuestion = data
    }
}
