package com.isarthaksharma.splitespree

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.isarthaksharma.splitespree.model.SessionManager
import com.isarthaksharma.splitespree.pages.*
import com.isarthaksharma.splitespree.ui.theme.SpliteSpreeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // session checking

        val sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, MainUiUserSection::class.java)
            startActivity(intent)
            finish()
        } else {
            setContent {
                SpliteSpreeTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        SetStatusBarColor(color = MaterialTheme.colorScheme.background)
                        NavigationControllerLogin(modifier = Modifier)
                    }
                }
            }
        }
    }
    @Composable
    fun SetStatusBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(color = color)
        }
    }
}

@Composable
fun NavigationControllerLogin(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = PageNavigation.LoginPage,

        // animations
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { -it } },
        popEnterTransition = { slideInHorizontally { -it } },
        popExitTransition = { slideOutHorizontally { it } }

    ) {
        composable<PageNavigation.LoginPage> {
            LoginPage(
                goToSignUpPage = {
                    navController.navigate(PageNavigation.SignUpPage)
                }
            )
        }

        composable<PageNavigation.SignUpPage> {
            SignUpPage(
                onGoBack = {
                    navController.popBackStack()
                },
                goToLoginPage = {
                    navController.popBackStack()
                    navController.navigate(PageNavigation.LoginPage)
                }
            )
        }
    }
}