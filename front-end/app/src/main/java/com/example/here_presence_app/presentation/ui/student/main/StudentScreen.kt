package com.example.here_presence_app.presentation.ui.student.main

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

data class Course(
    val subject: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val status: Boolean? // true: présent, false: absent, null: futur
)

@Composable
fun StudentScreen() {
    val subjects = listOf("Maths", "Physique", "Biologie")
    var selectedSubject by remember { mutableStateOf<String?>(null) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedStatus by remember { mutableStateOf<String>("Tous") }

    val mockCourses = remember { generateMockCourses(subjects) }

    val filteredCourses = remember(selectedSubject, selectedDate, selectedStatus) {
        mockCourses
            .filter {
                (selectedSubject == null || it.subject == selectedSubject) &&
                        (selectedDate == null || it.date == selectedDate) &&
                        (selectedStatus == "Tous" ||
                                (selectedStatus == "Présent" && it.status == true) ||
                                (selectedStatus == "Absent" && it.status == false))
            }
            .sortedBy { it.startTime }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Suivi des présences",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
            Icon(Icons.Default.AccountCircle, contentDescription = "Profil", tint = Color(0xFF1B5E20))
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Subject filter
        SubjectDropdown(subjects, selectedSubject) { selectedSubject = it }

        Spacer(modifier = Modifier.height(8.dp))

        // Status Filter
        StatusFilter(selectedStatus) { selectedStatus = it }

        Spacer(modifier = Modifier.height(8.dp))

        // Date Filters
        DateFilterRow(selectedDate, onDateSelected = { selectedDate = it })

        Spacer(modifier = Modifier.height(16.dp))

        // Result
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filteredCourses) { course ->
                CourseCard(course)
            }
            if (filteredCourses.isEmpty()) {
                item {
                    Text("Aucun cours trouvé", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun SubjectDropdown(subjects: List<String>, selected: String?, onSelect: (String?) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20))
        ) {
            Text(selected ?: "Toutes les matières", color = Color.White)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("Toutes les matières") }, onClick = {
                onSelect(null); expanded = false
            })
            subjects.forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = {
                    onSelect(it); expanded = false
                })
            }
        }
    }
}

@Composable
fun StatusFilter(current: String, onSelect: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf("Tous", "Présent", "Absent").forEach { status ->
            Button(
                onClick = { onSelect(status) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (current == status) Color(0xFFE1BEE7) else Color.LightGray
                )
            ) {
                Text(status)
            }
        }
    }
}

@Composable
fun DateFilterRow(date: LocalDate?, onDateSelected: (LocalDate?) -> Unit) {
    val today = LocalDate.now()
    val context = LocalContext.current

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = { onDateSelected(today) }) { Text("Aujourd’hui") }
        Button(onClick = { onDateSelected(today.with(DayOfWeek.MONDAY)) }) { Text("Semaine") }
        Button(onClick = { onDateSelected(today.withDayOfMonth(1)) }) { Text("Mois") }

        // Calendar button
        val year = date?.year ?: today.year
        val month = date?.monthValue?.minus(1) ?: today.monthValue - 1
        val day = date?.dayOfMonth ?: today.dayOfMonth

        val datePickerDialog = remember {
            DatePickerDialog(
                context,
                { _, y, m, d -> onDateSelected(LocalDate.of(y, m + 1, d)) },
                year, month, day
            )
        }

        Button(onClick = { datePickerDialog.show() }) {
            Icon(Icons.Default.CalendarToday, contentDescription = null)
        }
    }
}

@Composable
fun CourseCard(course: Course) {
    val statusColor = when (course.status) {
        true -> Color(0xFF1B5E20)
        false -> Color(0xFFC62828)
        null -> Color.Gray
    }
    val statusText = when (course.status) {
        true -> "Présent"
        false -> "Absent"
        null -> ""
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F4EA)),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(course.subject, fontWeight = FontWeight.Bold)
                Text("${course.date} - ${course.startTime.format(DateTimeFormatter.ofPattern("HH:mm"))}", fontSize = 12.sp)
            }
            if (statusText.isNotEmpty()) {
                Text(statusText, color = statusColor, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

fun generateMockCourses(subjects: List<String>): List<Course> = buildList {
    val today = LocalDate.now()
    val slots = listOf(8, 10, 14, 16)
    for (subj in subjects) {
        for (i in -3..3) {
            val date = today.plusDays(i.toLong())
            for (hour in slots) {
                val time = LocalTime.of(hour, 0)
                val isPast = date.isBefore(today) || (date == today && time.isBefore(LocalTime.now()))
                add(Course(subj, date, time, if (isPast) Random.nextBoolean() else null))
            }
        }
    }
    shuffle()
}
