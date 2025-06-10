package com.example.here_presence_app.data.repository

import com.example.here_presence_app.data.model.Student
import com.example.here_presence_app.network.ApiService

class PresenceRepository(private val api: ApiService) {
    suspend fun submitPresences(students: List<Student>) =
        api.submitPresences(students)
}
