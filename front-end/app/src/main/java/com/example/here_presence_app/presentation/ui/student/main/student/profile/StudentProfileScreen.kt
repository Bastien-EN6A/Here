package com.example.here_presence_app.presentation.ui.student.main.student.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.here_presence_app.R

data class StudentProfile(
    val name: String,
    val email: String,
    val phone: String,
    val id: String,
    val birthDate: String,
    val gender: String,
)

@Composable
fun StudentProfileScreen(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val student = StudentProfile(
        name = "Jack Miller",
        email = "jack@student.edu",
        phone = "06 12 34 56 78",
        id = "22404390",
        birthDate = "2001-04-25",
        gender = "Homme"
    )

    Box(modifier = Modifier.fillMaxSize()) {

        // 🌄 Image de fond avec zIndex 0f
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        )

        // 🔲 Overlay blanc avec zIndex 1f
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.85f))
                .zIndex(1f)
        )

        // 🧍 Contenu principal avec zIndex 2f
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .zIndex(2f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2E7D32))
                    .padding(20.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = student.name,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
            )
            Text(
                text = student.email,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )

            Spacer(Modifier.height(24.dp))

            ProfileItem("Téléphone", student.phone)
            ProfileItem("ID", student.id)
            ProfileItem("Date de naissance", student.birthDate)
            ProfileItem("Sexe", student.gender)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Se déconnecter", color = Color.White)
            }
        }

        // 🔐 Dialogue de confirmation
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirmer la déconnexion") },
                text = { Text("Voulez-vous vraiment vous déconnecter ?") },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        onLogout()
                    }) {
                        Text("Oui")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Annuler")
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(color = Color.DarkGray)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
        )
    }
}
