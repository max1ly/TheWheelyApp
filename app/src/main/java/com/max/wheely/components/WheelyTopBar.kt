package com.max.wheely.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.max.wheely.ui.theme.BluePrimary

@Composable
fun WheelyTopBar(title: String, modifier: Modifier = Modifier) {
    Surface(
        modifier
            .height(56.dp)
            .fillMaxWidth(),
        color = BluePrimary
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                modifier = Modifier.padding(horizontal = 12.dp),
                color = Color.White
            )
        }
    }
}
