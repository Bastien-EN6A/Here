package com.example.here_presence_app.presentation.ui.student.main.student.agenda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.ui.text.font.FontWeight


data class CourseEvent(
    val id: String,
    val subject: String,
    val startTime: String,
    val location: String,
    val status: String // "Présent", "Absent", "Excusé"
)

@Composable
fun AgendaScreen() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val mockSchedule = remember { generateMockSchedule() }
    val dailyCourses = remember(selectedDate) {
        mockSchedule.filter { it.date == selectedDate }.flatMap { it.events }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6FFF8))
            .padding(16.dp)
    ) {
        // Header + date picker
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CalendarToday, contentDescription = "Date", tint = Color(0xFF2E7D32))
            Spacer(Modifier.width(8.dp))
            TextButton(onClick = {
                // TODO: appeler un DatePickerDialog pour choisir date
            }) {
                Text(selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (dailyCourses.isEmpty()) {
                item {
                    Text("Aucun cours pour cette date", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                items(dailyCourses) { event ->
                    CourseItemCard(event)
                }
            }
        }
    }
}

@Composable
private fun CourseItemCard(event: CourseEvent) {
    Card(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(event.subject, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text("${event.startTime} – ${event.location}", style = MaterialTheme.typography.bodySmall)
            }
            Text(
                event.status,
                color = when(event.status) {
                    "Présent" -> Color(0xFF2E7D32)
                    "Absent" -> Color(0xFFC62828)
                    else -> Color.Gray
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private data class DaySchedule(val date: LocalDate, val events: List<CourseEvent>)

private fun generateMockSchedule(): List<DaySchedule> {
    val base = LocalDate.now()
    return listOf(
        DaySchedule(base.minusDays(1), listOf(
            CourseEvent("1","Maths","08:00","Salle 101","Présent"),
            CourseEvent("2","Physique","10:00","Salle 202","Absent")
        )),
        DaySchedule(base, listOf(
            CourseEvent("3","Biologie","14:00","Salle 303","Présent"),
            CourseEvent("4","EM","16:00","Salle 404","Excusé")
        )),
        DaySchedule(base.plusDays(1), listOf(
            CourseEvent("5","Informatique","08:00","Salle 101","Absent")
        ))
    )
}
