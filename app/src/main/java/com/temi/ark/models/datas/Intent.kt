package com.temi.ark.models.datas

data class IntentRequestDataModel(
    val call_no: String = "",
)

data class IntentResponseDataModel(
    val location: String,
)