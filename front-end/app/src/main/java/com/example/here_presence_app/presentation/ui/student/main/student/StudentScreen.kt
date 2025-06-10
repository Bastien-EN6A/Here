package com.example.here_presence_app.presentation.ui.student.main.student
/*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.here_presence_app.presentation.ui.student.main.student.agenda.AgendaScreen
import com.example.here_presence_app.presentation.ui.student.main.student.components.BottomNavBar
import com.example.here_presence_app.presentation.ui.student.main.student.main.StudentHomeScreen
import com.example.here_presence_app.presentation.ui.student.main.student.profile.StudentProfileScreen

@Composable
fun StudentScreen(rootNavController: NavHostController) {
    val studentNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(studentNavController)
        }
    ) { paddingValues ->
        NavHost(
            navController = studentNavController,
            startDestination = "student_home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("student_home") {
                StudentHomeScreen(studentNavController)
            }
            composable("student_agenda") {
                AgendaScreen()
            }
            composable("student_profile") {
                StudentProfileScreen(studentNavController)
            }
        }
    }
}*/

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.here_presence_app.presentation.ui.student.main.student.agenda.AgendaScreen
import com.example.here_presence_app.presentation.ui.student.main.student.main.StudentHomeScreen
import com.example.here_presence_app.presentation.ui.student.main.student.profile.StudentProfileScreen
import com.example.here_presence_app.presentation.ui.student.main.student.components.BottomNavBar
import androidx.compose.material3.Scaffold

@Composable
fun StudentScreen(
    navController: NavHostController, // contrôleur global (utilisé pour logout)
    onLogout: () -> Unit
) {
    // contrôleur local pour naviguer dans les écrans étudiant
    val studentNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(studentNavController)
        }
    ) { paddingValues ->
        NavHost(
            navController = studentNavController,
            startDestination = "student_home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("student_home") {
                StudentHomeScreen(studentNavController)
            }
            composable("student_agenda") {
                AgendaScreen()
            }
            composable("student_profile") {
                StudentProfileScreen(
                    navController = navController, // utilisé pour onLogout()
                    onLogout = onLogout
                )
            }
        }
    }
}


