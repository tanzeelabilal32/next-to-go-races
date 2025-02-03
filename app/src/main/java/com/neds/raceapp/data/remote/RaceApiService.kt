package com.neds.raceapp.data.remote

import com.neds.raceapp.data.model.RaceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RaceApiService {
    @GET("racing/")
    suspend fun getNextRaces(
        @Query("method") method: String = "nextraces",
        @Query("count") count: Int = 10
    ): RaceResponse
}