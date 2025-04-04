package com.temi.ark.views.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.temi.ark.R
import com.temi.ark.views.components.commons.ButtonComponent
import com.temi.ark.views.components.commons.InputComponent
import com.temi.ark.presenters.screens.ChatScreenPresenter
import com.temi.ark.views.components.layouts.NavBarComponent
import com.temi.ark.views.components.layouts.ChatListComponent

val presenter = ChatScreenPresenter()
val model = presenter.model

@Composable
fun ChatScreen(
    navController: NavController,
    context: Context? = null,
    lifecycle: Lifecycle? = null,
    question: String? = ""
) {
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(model.isLaunch) {
        lifecycle?.addObserver(presenter)
        model.coroutineScope = coroutineScope
        model.context = context
        model.defaultQuestion = question

        presenter.init()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(colorResource(R.color.white), colorResource(R.color.green_teal))
                )
            )
    ) {
        NavBarComponent(
            navController = navController,
            context = context,
            onBack = {
                presenter.handleStopSpeech()
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        if (model.isKeyboardVisible && model.isInputFieldFocused) {
                            focusManager.clearFocus()
                            presenter.handleFocusInput(false)
                        }
                    }
                },
        ) {
            ChatListComponent(
                messages = model.messages,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = if (model.isKeyboardVisible) 100.dp else 160.dp),
                itemSpacerHeight = if (model.isKeyboardVisible) 100.dp else 120.dp,
                listState = model.listState,
                context = context,
                navController = navController,
            )

            if (model.isKeyboardVisible) {
                InputComponent(
                    context = model.context,
                    value = model.textChat,
                    onValueChange = { presenter.handleChangeInput(it) },
                    onSendClick = {
                        if (model.textChat.isNotBlank()) {
                            presenter.fetchSendMessage()
                        }
                    },
                    onMicClick = { presenter.wakeUp() },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(8.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            presenter.handleFocusInput(focusState.isFocused)
                            if (focusState.isFocused) {
                                keyboardController?.show()
                            }
                        }
                )

                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(Color.Transparent),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ButtonComponent(
                        onDeleteClick = { presenter.fetchDeleteMessage() },
                        onMicClick = { presenter.wakeUp() },
                        onKeyboardClick = {
                            presenter.handleFocusInput(true)
                        },
                    )
                }
            }
        }
    }
}
