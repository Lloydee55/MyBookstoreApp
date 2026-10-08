package com.example.mybookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mybookapp.add_book_screen.AddBookScreen
import com.example.mybookapp.add_book_screen.AddScreenObject
import com.example.mybookapp.login.data.LoginScreenObject
import com.example.mybookapp.login.data.MainScreenDataObject
import com.example.mybookapp.main_screen.MainScreen
import com.example.mybookapp.ui.theme.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            val navController = rememberNavController()

            NavHost(navController = navController,
                startDestination = LoginScreenObject){

                composable<LoginScreenObject>{
                    LoginScreen{ navData ->
                        navController.navigate(navData)
                    }
                }

                composable<MainScreenDataObject> { navEntry ->
                    val navData = navEntry.toRoute<MainScreenDataObject>()
                    MainScreen(navData){
                        navController.navigate(AddScreenObject)
                    }
                }
                composable<AddScreenObject> { navEntry ->
                    AddBookScreen{
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}
