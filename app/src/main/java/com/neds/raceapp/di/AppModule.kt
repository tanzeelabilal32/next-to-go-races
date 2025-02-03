package com.neds.raceapp.di

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.neds.raceapp.data.remote.RaceApiService
import com.neds.raceapp.data.repository.RaceRepository
import com.neds.raceapp.domain.usecase.GetNextRacesUseCase
import com.neds.raceapp.presentation.viewmodel.RaceViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

    val appModule = module {
        // Gson instance
        single<Gson> {
            GsonBuilder()
                .setLenient() // Allows parsing malformed JSON
                .create()
        }

        // Logging Interceptor
        single {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Logs full response body
            }
        }

        // OkHttpClient
        single {
            OkHttpClient.Builder()
                .addInterceptor(get<HttpLoggingInterceptor>())
                .build()
        }

        // Retrofit instance
        single {
            Retrofit.Builder()
                .baseUrl("https://api.neds.com.au/rest/v1/")
                .addConverterFactory(GsonConverterFactory.create(get())) // Use Gson
                .client(get())
                .build()
                .create(RaceApiService::class.java)
        }


        // Repository
        single { RaceRepository(get()) }

        // Use Case
        single { GetNextRacesUseCase(get()) }

        // ViewModel
        viewModel { RaceViewModel(get()) }
    }
