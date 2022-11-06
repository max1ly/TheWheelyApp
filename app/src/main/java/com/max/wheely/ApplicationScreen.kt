package com.max.wheely

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.max.wheely.screens.WheelViewModel

@Composable
fun ApplicationScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val wheelViewModel: WheelViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = OptionsList.route,
        modifier = modifier,
    ) {

        composable(route = OptionsList.route) {
            OptionsList.screen(wheelViewModel, navController)
        }
        composable(route = Wheel.route) {
            Wheel.screen(wheelViewModel, navController)
        }
    }
}
