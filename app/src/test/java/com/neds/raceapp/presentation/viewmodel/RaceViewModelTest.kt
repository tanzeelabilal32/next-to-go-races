package com.neds.raceapp.presentation.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.neds.raceapp.data.model.AdvertisedStart
import com.neds.raceapp.data.model.Race
import com.neds.raceapp.domain.usecase.GetNextRacesUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
class RaceViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RaceViewModel
    private val useCase: GetNextRacesUseCase = mockk(relaxed = true) // ✅ Relaxed Mock

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

    companion object {
        private val mockRaces = listOf(
            Race("1", "Meeting A", 1, AdvertisedStart(1738167960),"9daef0d7-bf3c-4f50-921d-8e818c60fe61" ))
    }
}

