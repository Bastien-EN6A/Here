package com.ironmind.here

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ironmind.here.ui.screens.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onLoginSuccess = { email ->
                    Toast.makeText(this, "Bienvenue $email !", Toast.LENGTH_LONG).show()
                },
                onLoginFailed = {
                    Toast.makeText(this, "Identifiants incorrects", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
