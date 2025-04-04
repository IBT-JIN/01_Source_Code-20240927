package com.temi.ark.models.datas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class APIRequestDataModel {
    companion object {
        var arkToken = ""
        var arkSessionID = ""
    }
    var isLoading by mutableStateOf(false)
}

data class APIResponseDataModel(
    val statusCode: Int,
    val headers: Map<String, List<String>>? = null,
    val body: String? = null
)