package com.temi.ark.models.datas

import java.util.Date

data class ChatResponseDataModel(
    val id: String,
    val conversationId: String,
    val text: String,
    val createdAt: Date,
    val isBot: Boolean,
    val isTyping: Boolean = false,
    val payload: List<ARKPayloadResponseDataModel>? = null
)