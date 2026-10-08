package com.example.mybookapp.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mybookapp.ui.theme.MenuColor
import com.example.mybookapp.R
import com.example.mybookapp.ui.theme.StatusBar
import java.nio.file.WatchEvent
@Preview(
    showBackground = true
)
@Composable
fun DrawerHeader(email : String = "") {
    Column(
        Modifier.fillMaxWidth()
            .height(170.dp)
            .background(StatusBar),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(90.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "BooksStore App",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
            )
        Text(
            text = email,
            color = Color.Gray,
            fontSize = 16.sp
        )
    }
}