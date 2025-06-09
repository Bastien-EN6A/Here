package com.example.here_presence_app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.here_presence_app.presentation.ui.splash.SplashScreen
import com.example.here_presence_app.presentation.ui.login.LoginScreen
//import com.example.here_presence_app.presentation.ui.professor.ProfHomeScreen
import com.example.here_presence_app.presentation.ui.student.main.StudentScreen

@Composable
fun PresenceApp() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        //composable("prof_home") {
           // TeacherDashboardFragment(navController)
        //}
        composable("student_home") {
            StudentScreen()
        }
    }
}
