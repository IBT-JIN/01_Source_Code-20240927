package com.temi.ark.models.datas

data class ARKQueryRequestDataModel(
    val userId: String = "",
    val sessionId: String,
    val locale: String,
    val platform: String,
    val query: String,
)

data class ARKQueryResponseDataModel(
    val payload: List<ARKPayloadResponseDataModel>,
    val result: ARKResultResponseDataModel
)
