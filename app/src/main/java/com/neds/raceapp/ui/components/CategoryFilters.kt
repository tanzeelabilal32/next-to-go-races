package com.neds.raceapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material.Text
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.neds.raceapp.R
import com.neds.raceapp.presentation.viewmodel.RaceViewModel

@Composable
fun CategoryFilters(selectedCategories: List<String>, viewModel: RaceViewModel) {
    var filterVisible by remember { mutableStateOf(false) } // State to toggle filter visibility

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        // Filter Toggle Icon (Replaces Button)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { filterVisible = !filterVisible }
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Filter Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (filterVisible) {
            val categories = mapOf(
                "9daef0d7-bf3c-4f50-921d-8e818c60fe61" to "Greyhound",
                "161d9be2-e909-4326-8c2c-35ed71fb460b" to "Harness",
                "4a2788f8-e825-4d36-9894-efd4baf1cfae" to "Horse"
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                categories.forEach { (id, name) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    ) {
                        Checkbox(
                            checked = selectedCategories.contains(id),
                            onCheckedChange = { viewModel.toggleCategory(id) },
                            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(name, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //"Show All" with a Different Color
            Button(
                onClick = { viewModel.clearFilters() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Show All", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

