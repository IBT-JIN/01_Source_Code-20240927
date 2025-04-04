package com.temi.ark.presenters.screens

import com.temi.ark.models.datas.APIRequestDataModel
import com.temi.ark.models.views.screens.MainScreenViewModel
import com.temi.ark.utils.plugins.FetchARKPlugin
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainScreenPresenter {
    val model = MainScreenViewModel()
    private val fetchARKPlugin = FetchARKPlugin()
    private val singleThreadExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    fun init() {
        singleThreadExecutor.execute {
            Thread {
                fetchARKInit()
            }.start()
        }
    }

    private fun fetchARKInit() {
        model.context?.let { context ->
            fetchARKPlugin.fetchARKInit(
                context = context,
                callback = { data ->
                    if (data != null) {
                        APIRequestDataModel.arkToken = data.token
                        APIRequestDataModel.arkSessionID = data.sessionId
                    }
                }
            )
        }
    }
}