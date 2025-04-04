package com.temi.ark.presenters.components.commons

import android.os.CountDownTimer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener
import com.robotemi.sdk.navigation.model.SpeedLevel
import com.temi.ark.models.datas.ARKPayloadResponseDataModel
import com.temi.ark.models.views.components.commons.MessageCardComponentViewModel
import com.temi.ark.utils.plugins.FetchAppPlugin

class MessageCardComponentPresenter: DefaultLifecycleObserver, OnGoToLocationStatusChangedListener {
    private val robot: Robot = Robot.getInstance()
    val model = MessageCardComponentViewModel()
    private val fetchAppPlugin = FetchAppPlugin()

    private val timer = object: CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            if (model.timeCount > 0) {
                model.setCount(model.timeCount - 1)
            }
        }

        override fun onFinish() {
            if (!model.isReturned) {
                handleReturnHome()
            }
        }
    }

    fun init() {
        robot.addOnGoToLocationStatusChangedListener(this)
    }

    fun handleReturnHome() {
        model.setIsReturned(true)
        handleCloseModal()
        handleNavigate("입구")
        model.navController?.navigateUp()
    }

    fun handleSelectBook(item: ARKPayloadResponseDataModel) {
        model.setIsShowModal(true)
        model.setIsGoing(false)
        model.setIsArrived(false)
        model.setIsReturned(false)
        model.setCount(60)
        model.setBook(item)
        timer.cancel()
    }

    fun handleCloseModal() {
        model.setIsShowModal(false)
        model.setIsGoing(false)
        model.setIsArrived(false)
        model.setCount(60)
        model.setBook(null)
        timer.cancel()
    }

    fun goTo() {
        model.setIsGoing(true)
        val keyword = model.bookSelected?.call_no ?: ""
        if (keyword.contains("특화")) {
            var locate = ""
            val regCode = model.bookSelected?.reg_code
            if (regCode == "ZBJ") {
                locate = "우주특화아동 zbj"
            }
            if (regCode == "ZBE") {
                locate = "우주특화일반 zbe"
            }
            handleNavigate(locate)
        } else {
            model.context?.let { context ->
                model.bookSelected?.call_no?.let {
                    fetchAppPlugin.fetchIntent(
                        call_no = it,
                        context = context,
                        callback = { data ->
                            if (data != null) {
                                handleNavigate(data.location)
                            }
                        }
                    )
                }
            }
        }
    }

    private fun handleNavigate(locate: String) {
        for (location in robot.locations) {
            if (location.trim() == locate) {
                robot.goTo(
                    locate,
                    backwards = false,
                    noBypass = false,
                    speedLevel = SpeedLevel.HIGH
                )
                break
            }
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onDestroy(owner)
        robot.removeOnGoToLocationStatusChangedListener(this)
    }

    override fun onGoToLocationStatusChanged(
        location: String,
        status: String,
        descriptionId: Int,
        description: String
    ) {
        when(status) {
            "complete", "abort"-> {
                model.setIsArrived(true)
                model.setIsGoing(false)
                timer.start()
            }
        }
    }
}