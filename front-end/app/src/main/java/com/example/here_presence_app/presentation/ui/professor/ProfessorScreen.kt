package com.example.here_presence_app.presentation.ui.professor

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.itemsIndexed



@Composable
fun ProfessorScreen(
    navController: NavHostController,  // ajoutez ce paramètre
    viewModel: ProfessorViewModel = koinViewModel()
) {
    val students = viewModel.students
    val selectedClass by remember { derivedStateOf { viewModel.selectedClass } }
    val today = viewModel.today

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6FFF8))
            .padding(16.dp)
    ) {
        Text("Fiche de Présences", style = MaterialTheme.typography.headlineSmall)
        Text("$selectedClass – $today", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        ClassDropdown(
            classes = listOf("Statistiques - 1AIR", "Calcul matriciel - 2AIR"),
            selected = selectedClass
        ) { viewModel.selectedClass = it }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(students) { _, student ->
                StudentPresenceCard(student = student) { present ->
                    viewModel.togglePresence(student.id, present)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = { viewModel.markAllPresent() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("Marquer tout présents", color = Color.White)
            }
            Button(
                onClick = {
                    viewModel.validate(
                        onSuccess = { /* message ou toast */ },
                        onError = { /* snackbar d’erreur */ }
                    )
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("Valider", color = Color.White)
            }
        }
    }
}

@Composable
fun ClassDropdown(classes: List<String>, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(onClick = { expanded = true }) {
            Text(selected, color = Color.White)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            classes.forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = {
                    onSelect(it)
                    expanded = false
                })
            }
        }
    }
}
