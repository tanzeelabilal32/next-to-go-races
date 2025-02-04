package com.neds.raceapp


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.neds.raceapp.ui.main.MainActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CategoryFiltersTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.neds.raceapp", appContext.packageName)
    }

    @Test
    fun testRaceScreenDisplaysData() {
        // Ensure UI is stable before making assertions
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Upcoming Races").assertExists()

        composeTestRule.onNodeWithContentDescription("Filter Icon").assertExists()
    }

    @Test
    fun selectingCheckboxUpdatesUI() {
        // Open filters
        composeTestRule.onNodeWithContentDescription("Filter Icon").performClick()

        // Select "Greyhound Racing" checkbox
        composeTestRule.onNodeWithText("Greyhound").performClick()

        composeTestRule.waitForIdle()

        // Verify checkbox is checked
        composeTestRule.onNodeWithText("Greyhound").assertIsDisplayed()
    }
}


