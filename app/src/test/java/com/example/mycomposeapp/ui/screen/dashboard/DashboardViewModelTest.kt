package com.example.mycomposeapp.ui.screen.dashboard

import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.keys.PreferenceKeys
import com.example.mycomposeapp.domain.model.Location
import com.example.mycomposeapp.domain.usecase.datastore.GetPreferenceUseCase
import com.example.mycomposeapp.domain.usecase.datastore.SetPreferenceUseCase
import com.example.mycomposeapp.domain.usecase.location.GetLocationsUseCase
import com.example.mycomposeapp.domain.usecase.location.GetLocationsUseCaseTest
import com.example.mycomposeapp.util.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK private lateinit var getLocationsUseCase: GetLocationsUseCase
    @MockK private lateinit var getPreferenceUseCase: GetPreferenceUseCase
    @MockK private lateinit var setPreferenceUseCase: SetPreferenceUseCase

    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        viewModel = DashboardViewModel(
            getLocationsUseCase = getLocationsUseCase,
            getPreferenceUseCase = getPreferenceUseCase,
            setPreferenceUseCase = setPreferenceUseCase
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Given locations error When LoadLocations Then snackbar emitted`() = runTest {
        val error = "Network error"
        coEvery { getLocationsUseCase() } returns flow {
            emit(Resource.Loading)
            emit(Resource.Error(error))
        }
        viewModel.onEvent(DashboardContract.Event.LoadLocations)
        advanceUntilIdle()

        val effect = viewModel.sideEffect.first()
        assertEquals(DashboardContract.SideEffect.ShowSnackBar(error), effect)
    }

    @Test
    fun `ToggleTheme toggles from false to true and persists`() = runTest {
        assertFalse(viewModel.uiState.value.isDarkTheme)

        coEvery { setPreferenceUseCase(PreferenceKeys.DARK_MODE, true) } returns Unit

        viewModel.onEvent(DashboardContract.Event.ToggleTheme)
        advanceUntilIdle()

        coVerify(exactly = 1) { setPreferenceUseCase(PreferenceKeys.DARK_MODE, true) }
        assertTrue(viewModel.uiState.value.isDarkTheme)
    }

    @Test
    fun `ToggleTheme toggles from true to false and persists`() = runTest {
        coEvery { setPreferenceUseCase(PreferenceKeys.DARK_MODE, true) } returns Unit
        viewModel.onEvent(DashboardContract.Event.ToggleTheme)
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value.isDarkTheme)

        coEvery { setPreferenceUseCase(PreferenceKeys.DARK_MODE, false) } returns Unit
        viewModel.onEvent(DashboardContract.Event.ToggleTheme)
        advanceUntilIdle()

        coVerify(exactly = 1) { setPreferenceUseCase(PreferenceKeys.DARK_MODE, false) }
        assertFalse(viewModel.uiState.value.isDarkTheme)
    }

    @Test
    fun `LoadLocations - Loading then Success updates state`() = runTest {
        val domainLocation1 = mockk<com.example.mycomposeapp.domain.model.Location>(relaxed = true)
        val domainLocation2 = mockk<com.example.mycomposeapp.domain.model.Location>(relaxed = true)
        val domainList = listOf(domainLocation1, domainLocation2)

        val flow = flow {
            emit(Resource.Loading)
            emit(Resource.Success(domainList))
        }

        coEvery { getLocationsUseCase() } returns flow

        viewModel.onEvent(DashboardContract.Event.LoadLocations)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.errorMessage)
        assertEquals(2, state.locations.size)
    }

    @Test
    fun `Refresh triggers loadLocations (usecase invoked)`() = runTest {
        val flow = flow<Resource<List<Location>>> {
            emit(Resource.Loading)
            emit(Resource.Success(emptyList()))
        }
        coEvery { getLocationsUseCase() } returns flow

        viewModel.onEvent(DashboardContract.Event.Refresh)
        advanceUntilIdle()

        coVerify(exactly = 1) { getLocationsUseCase() }
    }

    @Test
    fun `LoadLocations - Error updates state and emits snackbar side effect`() = runTest {
        val errorMessage = "Network error"

        val flow = flow<Resource<List<Location>>> {
            emit(Resource.Loading)
            emit(Resource.Error(errorMessage))
        }
        coEvery { getLocationsUseCase() } returns flow

        val sideEffectDeferred = async {
            viewModel.sideEffect.first()
        }

        viewModel.onEvent(DashboardContract.Event.LoadLocations)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(errorMessage, state.errorMessage)

        val sideEffect = sideEffectDeferred.await()
        assertEquals(
            DashboardContract.SideEffect.ShowSnackBar(errorMessage),
            sideEffect
        )
    }

}
