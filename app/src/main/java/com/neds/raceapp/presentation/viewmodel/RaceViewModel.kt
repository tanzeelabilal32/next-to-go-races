package com.neds.raceapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neds.raceapp.data.model.Race
import com.neds.raceapp.domain.usecase.GetNextRacesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RaceViewModel(private val getNextRacesUseCase: GetNextRacesUseCase) : ViewModel() {

    private val _races = MutableStateFlow<List<Race>>(emptyList())
    val races: StateFlow<List<Race>> get() = _races

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _selectedCategories = MutableStateFlow<List<String>>(emptyList())
    val selectedCategories: StateFlow<List<String>> get() = _selectedCategories

    init {
        fetchRacesPeriodically()
    }

    private fun fetchRacesPeriodically() {
        viewModelScope.launch {
            while (true) {
                fetchRaces()
                delay(5 * 60 * 1000) // Refresh data every 5 minutes
            }
        }
    }

    fun fetchRaces() {
        viewModelScope.launch {

            _loading.value = true
            getNextRacesUseCase.execute().collect { result ->
                result.onSuccess { raceList ->
                    val currentTime = System.currentTimeMillis() / 1000
                    _races.value = raceList
                        .filter { it.advertisedStart.seconds > currentTime - 60 }
                        .filter {
                            _selectedCategories.value.isEmpty() || _selectedCategories.value.contains(
                                it.categoryId
                            )
                        }
                        .sortedBy { it.advertisedStart.seconds }
                        .take(5)
                }.onFailure {
                    _races.value = emptyList()
                }
                _loading.value = false
            }
        }
    }

    fun onRaceExpired(raceId: String) {
        removeExpiredRace(raceId)
        fetchRaces()
    }

    private fun removeExpiredRace(raceId: String) {
        _races.value = _races.value.filter { it.raceId != raceId } // Remove expired race
    }

    fun toggleCategory(categoryId: String) {
        _selectedCategories.value = if (_selectedCategories.value.contains(categoryId)) {
            _selectedCategories.value - categoryId
        } else {
            _selectedCategories.value + categoryId
        }
        fetchRaces()  // Refresh list when filter changes
    }

    fun clearFilters() {
        _selectedCategories.value = emptyList()
        fetchRaces() // Refresh list when filters are cleared
    }


}