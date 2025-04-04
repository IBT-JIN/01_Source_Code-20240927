package com.temi.ark.models.datas

data class ARKPayloadResponseDataModel(
    val title: String,
    val author: String,
    val reg_no: String,
    val reg_code: String,
    val book_key: String,
    val call_no: String,
    val ea_isbn: String,
    val loan_able: String,
    val shelf_loc_code: String,
    val image: String,
    val category: String,
)