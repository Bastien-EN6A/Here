package com.example.here_presence_app.core.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.here_presence_app.presentation.ui.login.LoginScreen
import com.example.here_presence_app.presentation.ui.professor.ProfessorScreen
import com.example.here_presence_app.presentation.ui.splash.SplashScreen
import com.example.here_presence_app.presentation.ui.student.main.student.StudentScreen

@Composable
fun PresenceApp() {
    val navController = rememberNavController()
    var isStudentLoggedIn by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        // 🟢 Splash screen
        composable("splash") {
            SplashScreen(navController)
        }

        // 🔐 Login
        composable("login") {
            LoginScreen(navController) {
                // Étudiant connecté avec succès
                isStudentLoggedIn = true
                navController.navigate("student_root") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }

        // 👨‍🏫 Professeur
        composable("teacher_home") {
            ProfessorScreen(navController)
        }

        // 🧑‍🎓 Accueil étudiant (NavHost secondaire)
        composable("student_root") {
            StudentScreen(
                navController = navController,
                onLogout = {
                    isStudentLoggedIn = false
                    navController.navigate("login") {
                        popUpTo("student_root") { inclusive = true }
                    }
                }
            )
        }
    }
}

/*package com.example.here_presence_app.core.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.here_presence_app.presentation.ui.login.LoginScreen
import com.example.here_presence_app.presentation.ui.professor.ProfessorScreen
import com.example.here_presence_app.presentation.ui.splash.SplashScreen
import com.example.here_presence_app.presentation.ui.student.main.student.StudentNavHost

@Composable
fun PresenceApp() {
    val navController = rememberNavController()
    var isStudentLoggedIn by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = if (isStudentLoggedIn) "student_root" else "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("login") {
            LoginScreen(navController) {
                // callback de succès de connexion étudiant
                isStudentLoggedIn = true
                navController.navigate("student_root") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
        composable("teacher_home") {
            ProfessorScreen(navController)
        }
        composable("student_root") {
            StudentNavHost(
                navController = navController,
                onLogout = {
                    isStudentLoggedIn = false
                    navController.navigate("login") {
                        popUpTo("student_root") { inclusive = true }
                    }
                }
            )
        }
    }
}*/