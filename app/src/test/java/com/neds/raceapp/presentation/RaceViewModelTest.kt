package com.neds.raceapp.presentation


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.neds.raceapp.data.model.AdvertisedStart
import com.neds.raceapp.data.model.Race
import com.neds.raceapp.domain.usecase.GetNextRacesUseCase
import com.neds.raceapp.presentation.viewmodel.RaceViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.rules.TestRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

@ExperimentalCoroutinesApi
class RaceViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RaceViewModel
    private val useCase: GetNextRacesUseCase = mockk(relaxed = true) // Relaxed Mock

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        //Mock API response
        coEvery { useCase.execute() } returns flowOf(Result.success(mockRaces))

        viewModel = RaceViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll() //Clears MockK objects to prevent memory leaks
    }


    @Test
    fun `fetchRaces should update race list`() = runTest {
        viewModel.fetchRaces()
        advanceUntilIdle() //Ensures coroutine execution

        viewModel.races.test {
            assertEquals(mockRaces, awaitItem()) // Expect mock races in ViewModel
        }
    }

    @Test
    fun `removeExpiredRace() removes race correctly`() = runTest {
        val mockRace = Race(
            "1",
            "Race A",
            1,
            AdvertisedStart(Companion.currentTime + 10000L),
            "9daef0d7-bf3c-4f50-921d-8e818c60fe61"
        )

        viewModel.fetchRaces() // Fetch races

        // Add race manually for test
        viewModel.onRaceExpired("1")

        // Check if the race list is empty
        assertFalse(viewModel.races.value.contains(mockRace))
    }

    @Test
    fun `toggleCategory() filters races by selected category`() = runTest {

        viewModel.fetchRaces()
        testDispatcher.scheduler.advanceUntilIdle()

        // Initially, both races are present
        assertTrue((viewModel.races.value).contains(horseRace))
        assertTrue((viewModel.races.value).contains(greyhoundRace))

        // Toggle category to filter only horse races
        viewModel.toggleCategory("4a2788f8-e825-4d36-9894-efd4baf1cfae")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue((viewModel.races.value).contains(horseRace))
        assertFalse((viewModel.races.value).contains(greyhoundRace))

        // Toggle category again to include all races
        viewModel.toggleCategory("4a2788f8-e825-4d36-9894-efd4baf1cfae")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue((viewModel.races.value).contains(horseRace))
        assertTrue((viewModel.races.value).contains(greyhoundRace))
    }

    @Test
    fun `clearFilters() resets selected categories and fetches all races`() = runTest {

        viewModel.fetchRaces()
        testDispatcher.scheduler.advanceUntilIdle()

        // Toggle a category to filter only horse races
        viewModel.toggleCategory("4a2788f8-e825-4d36-9894-efd4baf1cfae")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue((viewModel.races.value).contains(horseRace))
        assertFalse((viewModel.races.value).contains(greyhoundRace))

        //Clear filters
        viewModel.clearFilters()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue((viewModel.races.value).contains(horseRace))
        assertTrue((viewModel.races.value).contains(greyhoundRace))
    }

    companion object {
        val currentTime = System.currentTimeMillis() / 1000
        val horseRace = Race(
            raceId = "1",
            meetingName = "Melbourne Cup",
            raceNumber = 3,
            advertisedStart = AdvertisedStart(seconds = currentTime + 120),
            categoryId = "4a2788f8-e825-4d36-9894-efd4baf1cfae"
        )
        val greyhoundRace = Race(
            raceId = "2",
            meetingName = "Greyhound Derby",
            raceNumber = 4,
            advertisedStart = AdvertisedStart(seconds = currentTime + 200),
            categoryId = "9daef0d7-bf3c-4f50-921d-8e818c60fe61"
        )

        private val mockRaces = listOf(horseRace, greyhoundRace)
    }
}

