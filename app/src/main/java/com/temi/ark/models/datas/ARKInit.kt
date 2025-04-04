package com.temi.ark.models.datas

data class ARKInitRequestDataModel(
    val userId: String = "",
    val locale: String,
    val platform: String,
)

data class ARKInitResponseDataModel(
    val sessionId: String,
    val token: String,
)