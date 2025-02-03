package com.neds.raceapp.domain.usecase

import com.neds.raceapp.data.model.Race
import com.neds.raceapp.data.repository.RaceRepository
import kotlinx.coroutines.flow.Flow

class GetNextRacesUseCase(private val repository: RaceRepository) {
    suspend fun execute(): Flow<Result<List<Race>>> = repository.getNextRaces()
}