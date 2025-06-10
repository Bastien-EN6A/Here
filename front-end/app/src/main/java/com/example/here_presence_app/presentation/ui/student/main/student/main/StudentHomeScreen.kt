package com.example.here_presence_app.presentation.ui.student.main.student.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Canvas
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.here_presence_app.R

data class ModuleAbsence(val name: String, val absences: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(navController: NavHostController, studentName: String = "Jack") {
    var selectedPeriod by remember { mutableStateOf("Cette semaine") }
    val periodOptions = listOf("Aujourd’hui", "Cette semaine", "Ce mois")
    var expanded by remember { mutableStateOf(false) }

    val moduleData = when (selectedPeriod) {
        "Aujourd’hui" -> listOf(
            ModuleAbsence("Java", 1),
            ModuleAbsence("Math Discret", 0)
        )
        "Ce mois" -> listOf(
            ModuleAbsence("Java", 4),
            ModuleAbsence("Math Discret", 2),
            ModuleAbsence("Statistique", 3)
        )
        else -> listOf(
            ModuleAbsence("Java", 2),
            ModuleAbsence("Math Discret", 1),
            ModuleAbsence("Statistique", 2)
        )
    }


    val totalAbsences = moduleData.sumOf { it.absences }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // ✅ Fond image
        Image(
            painter = painterResource(id = R.drawable.background_bleu),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Bonjour, $studentName 👋",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Dropdown filtre période
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedPeriod,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Période") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                    },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    periodOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedPeriod = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ✅ Résumé carte avec cercle animé
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedProgressRing(
                        percentage = 1f, // animation uniquement visuelle
                        label = "Total absences: $totalAbsences"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    moduleData.forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(it.name, fontSize = 16.sp)
                            Text("${it.absences}", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedProgressRing(
    percentage: Float,
    label: String,
    modifier: Modifier = Modifier,
    size: Dp = 140.dp,
    strokeWidth: Dp = 14.dp,
    primaryColor: Color = Color(0xFF2E7D32),
    backgroundColor: Color = Color(0xFFE0E0E0)
) {
    var animatedProgress by remember { mutableStateOf(0f) }
    val animatedValue by animateFloatAsState(
        targetValue = animatedProgress,
        animationSpec = tween(1000),
        label = "ring"
    )

    LaunchedEffect(percentage) {
        animatedProgress = percentage
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = backgroundColor,
                style = Stroke(width = strokeWidth.toPx())
            )
            drawArc(
                color = primaryColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedValue,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("${(animatedValue * 100).toInt()}%", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(label, fontSize = 14.sp, color = Color.Gray)
        }
    }
}
