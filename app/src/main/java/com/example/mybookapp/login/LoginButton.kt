package com.example.mybookapp.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mybookapp.ui.theme.ButtonColor
import java.nio.file.WatchEvent

@Composable
fun LoginButton(
    text: String,
    onClick: () -> Unit
) {
    Button(onClick = {
        onClick()
    },
        modifier = Modifier.fillMaxWidth(0.5f),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor
        )
        ) {
        Text(text = text)
    }
}