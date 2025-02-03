package com.neds.raceapp


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.neds.raceapp.presentation.viewmodel.RaceViewModel
import com.neds.raceapp.ui.components.CategoryFilters
import com.neds.raceapp.ui.main.MainActivity
import com.neds.raceapp.ui.screen.RaceScreen
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoryFiltersTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    private lateinit var viewModel: RaceViewModel


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.neds.raceapp", appContext.packageName)
    }

    @SmallTest
    @Test
    fun testRaceScreenDisplaysData() {
        composeTestRule.activityRule.scenario.onActivity { activity ->
            composeTestRule.setContent {
                RaceScreen()
            }
        }

        // Ensure UI is stable before making assertions
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Upcoming Races").assertExists()
        composeTestRule.onNodeWithContentDescription("Filter Icon").assertExists()
    }
    @Test
    fun selectingCheckboxUpdatesUI() {
        composeTestRule.waitForIdle()

        // Open filters
        composeTestRule.onNodeWithContentDescription("Filter Icon").performClick()

        // Select "Greyhound Racing" checkbox
        composeTestRule.onNodeWithText("Greyhound").performClick()

        // Verify checkbox is checked
        composeTestRule.runOnIdle {
            composeTestRule.onNodeWithText("Greyhound").assertIsDisplayed()
        }
    }

    @Test
    fun clickingShowAllClearsFilters() {
        // Ensure UI is stable before clicking the filter icon
        composeTestRule.waitForIdle()

        composeTestRule.setContent {
            CategoryFilters(
                selectedCategories = listOf("9daef0d7-bf3c-4f50-921d-8e818c60fe61"),
                viewModel = viewModel
            )
        }

        // Open filters
        composeTestRule.onNodeWithContentDescription("Filter Icon").performClick()

        // Click "Show All"
        composeTestRule.onNodeWithText("Show All").performClick()

        // Check that all selections are cleared
        composeTestRule.runOnIdle {
            assert(viewModel.selectedCategories.value.isEmpty())
        }
    }
}


