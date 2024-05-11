package com.example.admin

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.admin.Screen.AddProduct
import com.example.admin.Screen.DisplayProduct
import com.example.admin.ui.theme.Test2Theme
import dagger.hilt.android.AndroidEntryPoint
sealed class DestinationScreen(var route : String) {
    object Add : DestinationScreen("add")
    object Display : DestinationScreen("display")
}
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Test2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddApp()
                }
            }
        }
    }
    @Composable
    fun AddApp( ){
        val navControler = rememberNavController()
        var vm = hiltViewModel< Model>()

        NavHost(navController = navControler, startDestination = DestinationScreen.Display.route ) {
            composable(DestinationScreen.Display.route){
                DisplayProduct(navControler,vm )
            }
            composable(DestinationScreen.Add.route){
                AddProduct( navController =navControler , vm = vm)
            }
        }
        }
}
