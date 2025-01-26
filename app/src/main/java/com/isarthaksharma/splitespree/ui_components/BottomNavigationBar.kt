package com.isarthaksharma.splitespree.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person2
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.isarthaksharma.splitespree.R

val items:List<BottomNavigationItemData> = listOf(
    BottomNavigationItemData(
        title = "Self",
        icon = Icons.Rounded.Person2
    ),

    BottomNavigationItemData(
        title = "Groups",
        icon = Icons.Filled.Groups

    ),

    BottomNavigationItemData(
        title = "Notification",
        icon = Icons.Rounded.Notifications
    ),

    BottomNavigationItemData(
        title = "Account",
        icon = Icons.Rounded.AccountCircle
    )

)


@Composable
fun BottomNavigationBar(navController: NavHostController){
    val currentDestination by navController.currentBackStackEntryAsState()

    NavigationBar {
        Row(modifier = Modifier.background(MaterialTheme.colorScheme.background.copy(alpha = 0.2f))) {
            items.forEach{ item ->
                if (currentDestination != null) {
                    NavigationBarItem(
                        selected = currentDestination?.destination?.route == item.title,
                        onClick = {
                            navController.navigate(item.title){
                                popUpTo(navController.graph.startDestinationId){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = MaterialTheme.colorScheme.onBackground)
                        },
                        label = {
                            Text(
                                text = item.title,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.doto, FontWeight.Medium)),
                                fontSize = 12.sp
                            )
                        }

                    )
                }
            }
        }
    }
}