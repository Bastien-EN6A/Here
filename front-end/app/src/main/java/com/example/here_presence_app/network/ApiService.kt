package com.example.here_presence_app.network

import com.example.here_presence_app.data.model.Student
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/presences")
    suspend fun submitPresences(@Body students: List<Student>): Response<Unit>
}
