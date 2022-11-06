package com.max.wheely

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.max.wheely.screens.OptionsScreen
import com.max.wheely.screens.WheelScreen
import com.max.wheely.screens.WheelViewModel

typealias WheelScreenProvider = @Composable (WheelViewModel, NavController) -> Unit

interface WheelyDestination {
    val route: String
    val title: String
    val screen: WheelScreenProvider
}

object OptionsList : WheelyDestination {
    override val route = "options_list"
    override val title = "Select option"
    override val screen: WheelScreenProvider = { viewModel, navController ->
        OptionsScreen(
            viewModel = viewModel,
            navController = navController,
        )
    }
}

object Wheel : WheelyDestination {
    override val route = "wheel_screen"
    override val title = "Spin a wheel"
    override val screen: WheelScreenProvider = { viewModel, navController ->
        WheelScreen(
            viewModel = viewModel,
            navController = navController,
        )
    }
}
