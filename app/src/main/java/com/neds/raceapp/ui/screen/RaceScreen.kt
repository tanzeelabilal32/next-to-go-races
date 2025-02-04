package com.neds.raceapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neds.raceapp.data.model.Race
import com.neds.raceapp.presentation.viewmodel.RaceViewModel
import com.neds.raceapp.ui.components.CategoryFilters
import com.neds.raceapp.ui.components.RaceCountdown
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun RaceScreen() {
    val viewModel: RaceViewModel = koinViewModel()
    val races by viewModel.races.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val selectedCategories by viewModel.selectedCategories.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchRacesPeriodically()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Upcoming Races", color = MaterialTheme.colorScheme.onPrimary) },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            // Category Filters
            CategoryFilters(selectedCategories, viewModel)

            if (loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {

                // Races List
                LazyColumn {
                    items(races) { race ->
                        RaceItem(race, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun RaceItem(race: Race, viewModel: RaceViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("${race.meetingName}", style = MaterialTheme.typography.titleMedium)
            Text("Race No: ${race.raceNumber}")
            RaceCountdown(race.advertisedStart.seconds, race.raceId, viewModel)
        }
    }
}
