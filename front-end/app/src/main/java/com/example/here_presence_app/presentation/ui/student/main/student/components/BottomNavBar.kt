package com.example.here_presence_app.presentation.ui.student.main.student.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.here_presence_app.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ExitToApp

sealed class BottomNavItem(val route: String, val label: String, val icon: Int) {
    object Home      : BottomNavItem("student_home", "Accueil",      R.drawable.ic_home)
    object Agenda    : BottomNavItem("student_agenda", "Agenda",     R.drawable.ic_calendar)
    object Profile   : BottomNavItem("student_profile", "Profil",     R.drawable.ic_profile)
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Accueil") },
            selected = currentRoute == "student_home",
            onClick = { navController.navigate("student_home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Agenda") },
            selected = currentRoute == "student_agenda",
            onClick = { navController.navigate("student_agenda") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profil") },
            selected = currentRoute == "student_profile",
            onClick = { navController.navigate("student_profile") }
        )
    }
}
/*@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Agenda,
        BottomNavItem.Profile
    )
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(containerColor = Color.White, contentColor = Color(0xFF2E7D32)) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Accueil") },
            selected = false,
            onClick = { navController.navigate("student_home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Agenda") },
            selected = false,
            onClick = { navController.navigate("agenda") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profil") },
            selected = false,
            onClick = { navController.navigate("profil") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Déconnexion") },
            selected = false,
            onClick = {
                navController.navigate("login") {
                    popUpTo("student_home") { inclusive = true }
                }
            }
        )
    }
},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF2E7D32),
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color(0xFF2E7D32),
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}*/
