package com.neds.raceapp.ui.components


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.neds.raceapp.presentation.viewmodel.RaceViewModel
import kotlinx.coroutines.delay

@Composable
fun RaceCountdown(startTimeSeconds: Long, raceId: String, viewModel: RaceViewModel) {
    var remainingTime by remember { mutableStateOf(startTimeSeconds - (System.currentTimeMillis() / 1000)) }

    LaunchedEffect(startTimeSeconds) {
        while (remainingTime > -60) { // Allow race to stay visible for 1 extra minute
            delay(1000)
            remainingTime = startTimeSeconds - (System.currentTimeMillis() / 1000)
        }
        viewModel.onRaceExpired(raceId) // Remove race when expired
    }


    Text(
        text = if (remainingTime > 0) {
            "Starts in: ${remainingTime / 60}m ${remainingTime % 60}s"
        } else {
            "Race Started"
        },
        style = typography.labelMedium,
        color = if (remainingTime > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    )
}