package com.example.here_presence_app.core.di

import com.example.here_presence_app.data.repository.PresenceRepository
import com.example.here_presence_app.network.ApiService
import com.example.here_presence_app.presentation.ui.professor.ProfessorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<ApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.example.com/") // à adapter
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    single { PresenceRepository(get()) }

    viewModel { ProfessorViewModel(get()) }
}
