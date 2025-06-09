package com.example.here_presence_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.here_presence_app.core.navigation.PresenceApp
import com.example.here_presence_app.ui.theme.Here_Presence_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Here_Presence_appTheme {
                PresenceApp()
            }
        }
    }
}
