package com.example.here_presence_app.presentation.ui.professor

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.here_presence_app.data.model.Student

@Composable
fun StudentPresenceCard(student: Student, onToggle: (Boolean) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(student.name, style = MaterialTheme.typography.bodyLarge)
                Text("Matricule: ${student.matricule}", style = MaterialTheme.typography.bodySmall)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = { onToggle(true) }) {
                    Icon(Icons.Default.Check, contentDescription = "Présent", tint = if (student.present) Color(0xFF2E7D32) else Color.Gray)
                }
                IconButton(onClick = { onToggle(false) }) {
                    Icon(Icons.Default.Close, contentDescription = "Absent", tint = if (!student.present) Color(0xFFC62828) else Color.Gray)
                }
            }
        }
    }
}
