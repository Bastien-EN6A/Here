package com.example.here_presence_app.data.model

data class Student(
    val id: String,
    val name: String,
    val matricule: String,
    val photoUrl: String? = null,
    val present: Boolean
)
