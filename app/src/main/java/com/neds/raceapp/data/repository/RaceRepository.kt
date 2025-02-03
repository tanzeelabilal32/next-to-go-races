package com.neds.raceapp.data.repository

import android.util.Log
import com.neds.raceapp.data.model.Race
import com.neds.raceapp.data.remote.RaceApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class RaceRepository(private val apiService: RaceApiService) {

    suspend fun getNextRaces(): Flow<Result<List<Race>>> = flow {
        try {
            val response = apiService.getNextRaces()
            val raceSummaries = response.data.raceSummaries
            val races = response.data.nextToGoIds.mapNotNull { raceSummaries[it] }
                .sortedBy { it.advertisedStart.seconds }

            emit(Result.success(races))
        } catch (e: IOException) {
            emit(Result.failure(e))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
