package com.temi.ark.utils.plugins

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.temi.ark.models.datas.APIRequestDataModel
import com.temi.ark.models.datas.APIResponseDataModel
import com.temi.ark.models.datas.IntentRequestDataModel
import com.temi.ark.models.datas.IntentResponseDataModel
import com.temi.ark.utils.configs.EnvConfig
import org.json.JSONException
import org.json.JSONObject

class FetchAppPlugin {
    private fun sendRequest(
        url: String,
        method: Int = Request.Method.GET,
        body: String? = null,
        context: Context,
        callback: (response: APIResponseDataModel?) -> Unit?
    ) {
        val requestQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(method, EnvConfig.appAPIUrl + url, JSONObject(body),
            { response ->
                try {
                    callback(APIResponseDataModel(statusCode = 200, body = response.toString()))
                } catch (err: JSONException) {
                    callback(APIResponseDataModel(statusCode = 500, body = err.toString()))
                }
            },
            { err ->
                callback(APIResponseDataModel(statusCode = 400, body = err.toString()))
            }
        ).apply {
            retryPolicy = DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        }

        requestQueue.add(jsonObjectRequest)
    }

    fun fetchIntent(
        call_no: String,
        context: Context,
        callback: (response: IntentResponseDataModel?) -> Unit?
    ) {
        APIRequestDataModel().isLoading = true
        sendRequest(
            url = "location",
            method = Request.Method.POST,
            body = Gson().toJson(
                IntentRequestDataModel(
                    call_no = call_no
                )
            ),
            context = context,
            callback = { response ->
                if (response != null) {
                    if (response.statusCode == 200) {
                        callback(Gson().fromJson(response.body, IntentResponseDataModel::class.java))
                    }
                    callback(null)
                    APIRequestDataModel().isLoading = false
                }
            }
        )
    }
}