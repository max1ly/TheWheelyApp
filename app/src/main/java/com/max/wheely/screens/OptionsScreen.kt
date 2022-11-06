package com.max.wheely.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.max.wheely.Wheel
import com.max.wheely.domain.Option

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OptionsScreen(
    viewModel: WheelViewModel,
    navController: NavController,
) {
    val options = viewModel.options.collectAsState()
    val onOptionSelected: (Option) -> Unit = { viewModel.onOptionSelected(it) }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .nestedScroll(rememberNestedScrollInteropConnection())
            .weight(1f, false)
            .fillMaxHeight()
        ) {
            LazyColumn {
                items(options.value) { option ->
                    OptionItem(
                        option = option,
                        onClick = onOptionSelected
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                navController.navigate(Wheel.route)
            },
            enabled = true,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(56.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Done")
        }
    }
}

@Composable
fun OptionItem(option: Option, onClick: (Option) -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clickable { onClick(option) }
            .background(if (option.selected) Color.LightGray else Color.White),
    ) {
        Text(
            text = option.message,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(vertical = 16.dp),
        )
        Divider()
    }
}
