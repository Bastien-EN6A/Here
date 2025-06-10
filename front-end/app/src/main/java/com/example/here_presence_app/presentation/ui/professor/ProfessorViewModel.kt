package com.example.here_presence_app.presentation.ui.professor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.here_presence_app.data.model.Student
import com.example.here_presence_app.data.repository.PresenceRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import androidx.compose.runtime.*

class ProfessorViewModel(private val repo: PresenceRepository) : ViewModel() {

    var selectedClass by mutableStateOf("Statistiques - 1AIR")
    val today: LocalDate = LocalDate.now()

    var students = mutableStateListOf<Student>()
        private set

    init {
        // MOCK DATA - à remplacer par API
        students.addAll(
            listOf(
                Student("1", "BAOU SAMI", "123456", present = true),
                Student("2", "DIDIERJEAN BASTIEN", "654321", present = true),
                Student("3", "DIOP SAKHEWAR", "112233", present = true),
                Student("4", "FERHANE NIAMA", "445566", present = true),
                Student("5", "KHOURCHAFI HODEIFA", "435566", present = true),
                Student("6", "TRINH WILLIAM", "475566", present = true),
                Student("7", "KHOURCHAFI NILS", "455566", present = true)

            )
        )
    }

    fun togglePresence(id: String, present: Boolean) {
        val index = students.indexOfFirst { it.id == id }
        if (index != -1) {
            students[index] = students[index].copy(present = present)
        }
    }

    fun markAllPresent() {
        students.replaceAll { it.copy(present = true) }
    }

    fun validate(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                repo.submitPresences(students)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Erreur inconnue")
            }
        }
    }
}
