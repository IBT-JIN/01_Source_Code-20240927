package com.temi.ark.utils.plugins

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.google.gson.Gson
import com.temi.ark.models.datas.APIRequestDataModel
import com.temi.ark.models.datas.APIResponseDataModel
import com.temi.ark.models.datas.ARKInitRequestDataModel
import com.temi.ark.models.datas.ARKInitResponseDataModel
import com.temi.ark.models.datas.ARKQueryRequestDataModel
import com.temi.ark.models.datas.ARKQueryResponseDataModel
import com.temi.ark.utils.configs.EnvConfig
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class FetchARKPlugin {
    private fun sendRequest(
        url: String,
        method: Int = Request.Method.GET,
        body: String? = null,
        context: Context,
        callback: (response: APIResponseDataModel?) -> Unit?
    ) {
        val requestQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(method, EnvConfig.arkAPIUrl + url, JSONObject(body),
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

    fun fetchARKInit(
        context: Context,
        callback: (response: ARKInitResponseDataModel?) -> Unit?
    ) {
        sendRequest(
            url = "init",
            method = Request.Method.POST,
            body = Gson().toJson(
                ARKInitRequestDataModel(
                    locale = "ko_KR",
                    platform = "WEB",
                )
            ),
            context = context,
            callback = { response ->
                if (response != null) {
                    if (response.statusCode == 200) {
                        callback(Gson().fromJson(response.body, ARKInitResponseDataModel::class.java))
                    }
                    callback(null)
                }
            }
        )
    }

    fun fetchARKQuery(
        query: String,
        context: Context,
        callback: (response: ARKQueryResponseDataModel?) -> Unit?
    ) {
        sendRequest(
            url = "query",
            method = Request.Method.POST,
            body = Gson().toJson(
                ARKQueryRequestDataModel(
                    locale = "ko_KR",
                    query = query,
                    platform = "WEB",
                    sessionId = APIRequestDataModel.arkSessionID,
                )
            ),
            context = context,
            callback = { response ->
                if (response != null) {
                    if (response.statusCode == 200) {
                        callback(Gson().fromJson(response.body, ARKQueryResponseDataModel::class.java))
                    }
                    callback(null)
                }
            }
        )
    }
}