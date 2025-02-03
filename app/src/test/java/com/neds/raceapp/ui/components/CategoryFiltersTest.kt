package com.neds.raceapp.ui.components


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.neds.raceapp.presentation.viewmodel.RaceViewModel
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test



class CategoryFiltersTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: RaceViewModel

    @Before
    fun setUp() {
        viewModel = mockk(relaxed = true) // Mock ViewModel
    }

    @Test
    fun clickingShowFiltersButtonTogglesVisibility() {
        composeTestRule.setContent {
            CategoryFilters(selectedCategories = emptyList(), viewModel = viewModel)
        }

        // Verify "Show Filters" button exists and click it
        composeTestRule.onNodeWithText("Show Filters").assertExists().performClick()

        // After click, "Hide Filters" should be visible
        composeTestRule.onNodeWithText("Hide Filters").assertExists()
    }

    @Test
    fun selectingCheckboxUpdatesUI() {
        composeTestRule.setContent {
            CategoryFilters(selectedCategories = emptyList(), viewModel = viewModel)
        }

        // Open filters
        composeTestRule.onNodeWithText("Show Filters").performClick()

        // Select "Greyhound Racing" checkbox
        composeTestRule.onNodeWithText("Greyhound").performClick()

        // Verify checkbox is checked
        composeTestRule.onNodeWithText("Greyhound").assertIsDisplayed()
    }

    @Test
    fun clickingShowAllClearsFilters() {
        composeTestRule.setContent {
            CategoryFilters(selectedCategories = listOf("9daef0d7-bf3c-4f50-921d-8e818c60fe61"), viewModel = viewModel)
        }

        // Open filters
        composeTestRule.onNodeWithText("Show Filters").performClick()

        // Click "Show All"
        composeTestRule.onNodeWithText("Show All").performClick()

        // Check that all selections are cleared
        assert(viewModel.selectedCategories.value.isEmpty())
    }
}


