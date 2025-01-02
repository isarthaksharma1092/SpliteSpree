package com.isarthaksharma.splitespree.pages

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.isarthaksharma.splitespree.ui.theme.SpliteSpreeTheme
import com.isarthaksharma.splitespree.ui_components.BottomNavigationBar

class MainUiUserSection : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpliteSpreeTheme {
                MainUiUserSection(context = LocalContext.current)
            }
        }
    }
}

@Composable
fun MainUiUserSection(context: Context){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {BottomNavigationBar(navController)}
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = "Self",
            Modifier.padding(innerPadding),

            // Animations
            enterTransition = { slideInVertically { it } },
            exitTransition = { slideOutVertically { it } },
            popEnterTransition = { slideInVertically { it } },
            popExitTransition = { slideOutVertically { it } }
        ) {
            composable("Self") { Bottom_Self() }
            composable("Groups") { Bottom_Groups() }
            composable("Notification") { Bottom_Notification() }
            composable("Account") { Bottom_Account() }
        }
    }
}
