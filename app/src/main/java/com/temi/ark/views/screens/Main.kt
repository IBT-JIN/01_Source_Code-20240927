package com.temi.ark.views.screens

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.temi.ark.R
import com.temi.ark.presenters.screens.MainScreenPresenter

class MainScreen: AppCompatActivity() {
    private val presenter = MainScreenPresenter()
    private val model = presenter.model
    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.context = context
        presenter.init()

        setContent {
            render()
        }
    }

    @Preview
    @Composable
    private fun render() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = context.getString(R.string.router_home)) {
            composable(context.getString(R.string.router_home)) { HomeScreen(navController = navController, context = context) }
            composable(
                route = context.getString(R.string.router_chat) + "/{question}",
                arguments = listOf(
                    navArgument("question") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                val question = backStackEntry.arguments?.getString("question") ?: ""
                ChatScreen(navController = navController, context = context, lifecycle = lifecycle, question = question)
            }
        }
    }
}
