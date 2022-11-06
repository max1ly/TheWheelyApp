package com.max.wheely

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.max.wheely.components.WheelyTopBar
import com.max.wheely.ui.theme.WheelyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WheelyApp()
        }
    }
}

@Composable
fun WheelyApp() {
    WheelyTheme {
        Scaffold(
            topBar = { WheelyTopBar(title = "Wheely") },
        ) {
            ApplicationScreen(modifier = Modifier.padding(it))
        }
    }
}
