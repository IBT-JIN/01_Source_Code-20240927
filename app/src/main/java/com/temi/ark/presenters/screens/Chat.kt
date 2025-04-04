package com.temi.ark.presenters.screens

import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.robotemi.sdk.Robot
import com.robotemi.sdk.SttLanguage
import com.robotemi.sdk.listeners.OnConversationStatusChangedListener
import com.robotemi.sdk.listeners.OnRobotReadyListener
import com.temi.ark.R
import com.temi.ark.models.datas.ARKPayloadResponseDataModel
import com.temi.ark.models.datas.ChatResponseDataModel
import com.temi.ark.models.views.screens.ChatScreenViewModel
import com.temi.ark.utils.plugins.FetchARKPlugin
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ChatScreenPresenter: DefaultLifecycleObserver, OnRobotReadyListener, Robot.AsrListener,
    OnConversationStatusChangedListener {
    val model = ChatScreenViewModel()
    private val fetchARKPlugin = FetchARKPlugin()
    private val robot: Robot = Robot.getInstance()
    private var textToSpeech: TextToSpeech? = null
    private val singleThreadExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    fun init() {
        fetchDeleteMessage()
        textToSpeech = TextToSpeech(model.context) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech?.language = Locale.ENGLISH
            }
        }
    }

    fun wakeUp() {
        robot.wakeup(listOf(SttLanguage.EN_US, SttLanguage.SYSTEM))
        handleStopSpeech()
    }

    fun handleStopSpeech() {
        textToSpeech?.stop()
    }

    private suspend fun scrollToBottom(withAnimation: Boolean = true) {
        val lastIndex = model.messages.size - 1
        if (withAnimation) {
            model.listState.animateScrollToItem(
                index = lastIndex,
                scrollOffset = 2,
            )
        } else {
            model.listState.scrollToItem(lastIndex)
        }
    }

    private fun addMessage(
        message: String = "",
        isBot: Boolean = false,
        isTyping: Boolean = false,
        payload: List<ARKPayloadResponseDataModel>? = null
    ) {
        val messages = mutableStateListOf<ChatResponseDataModel>()
        messages.addAll(model.messages)
        if (messages.size > 0 && messages.last().isTyping) {
            messages.removeLast()
        }
        messages.add(
            ChatResponseDataModel(
                id = (messages.size + 1).toString(),
                conversationId = "1",
                text = message,
                createdAt = Date(),
                isBot = isBot,
                isTyping = isTyping,
                payload = payload,
            )
        )
        model.setMessages(messages)
    }

    fun handleFocusInput(isFocused: Boolean) {
        model.setIsKeyboardVisible(isFocused)
        model.setIsInputFieldFocused(isFocused)
    }

    fun handleChangeInput(text: String) {
        model.setChat(text)
    }

    fun fetchDeleteMessage() {
        var messages = mutableStateListOf<ChatResponseDataModel>()
        model.setMessages(messages)

        singleThreadExecutor.execute {
            Thread {
                if (model.defaultQuestion != " ") {
                    handleChangeInput(model.defaultQuestion!!)
                    fetchSendMessage()
                    model.setQuestionDefault(" ")
                } else {
                    fetchARKQuery(model.context?.getString((R.string.init_query))!!)
                }
            }.start()
        }
    }

    fun fetchSendMessage() {
        handleFocusInput(false)
        val text = model.textChat
        addMessage(text)
        model.setChat("")

        singleThreadExecutor.execute {
            Thread {
                fetchARKQuery(text)
            }.start()
        }
    }

    private fun fetchARKQuery(query: String) {
        handleStopSpeech()
        addMessage(isBot = true, isTyping = true)
        model.coroutineScope?.launch {
            scrollToBottom()
        }
        model.context?.let { context ->
            fetchARKPlugin.fetchARKQuery(
                query = query,
                context = context,
                callback = { data ->
                    if (data != null) {
                        val textReply = data.result.simpleResponses.simpleResponses[0].displayText
                        textToSpeech!!.speak(textReply, TextToSpeech.QUEUE_FLUSH, null, null)
                        addMessage(message = textReply, payload = data.payload, isBot = true)
                        model.coroutineScope?.launch {
                            scrollToBottom()
                        }
                    }
                }
            )
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        robot.addOnRobotReadyListener(this)
        robot.addAsrListener(this)
        robot.addOnConversationStatusChangedListener(this)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        robot.addOnRobotReadyListener(this)
        robot.addAsrListener(this)
        robot.addOnConversationStatusChangedListener(this)
    }

    override fun onRobotReady(isReady: Boolean) {
        if (isReady) {
            robot.hideTopBar()
        }
    }

    override fun onAsrResult(asrResult: String, sttLanguage: SttLanguage) {
        robot.finishConversation()
        handleChangeInput(asrResult)
        fetchSendMessage()
    }

    override fun onConversationStatusChanged(status: Int, text: String) {
        Log.d("Speech Status", status.toString())
        when (status) {
            OnConversationStatusChangedListener.IDLE -> {
                Log.d("Speech Status", "Status: IDLE | Text: $text")
            }

            OnConversationStatusChangedListener.LISTENING -> {
                Log.d("Speech Status", "Status: LISTENING | Text: $text")
            }

            OnConversationStatusChangedListener.THINKING -> {
                Log.d("Speech Status", "Status: THINKING | Text: $text")
            }

            OnConversationStatusChangedListener.SPEAKING -> {
                Log.d("Speech Status", "Status: SPEAKING | Text: $text")
            }

            else -> {
                Log.d("Speech Status", "Status: UNKNOWN | Text: $text")
            }
        }
    }
}
