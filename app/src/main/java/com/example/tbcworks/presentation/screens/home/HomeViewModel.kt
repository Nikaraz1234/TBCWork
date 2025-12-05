    package com.example.tbcworks.presentation.screens.home

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.tbcworks.domain.usecase.GetLocationsUseCase
    import com.example.tbcworks.presentation.screens.home.mapper.toPresentation
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.first
    import kotlinx.coroutines.launch
    import javax.inject.Inject

    @HiltViewModel
    class HomeViewModel @Inject constructor(
        private val getLocationsUseCase: GetLocationsUseCase
    ) : ViewModel() {

        private val _state = MutableStateFlow(HomeState())
        val state: StateFlow<HomeState> = _state

        fun loadLocations() {
            println("Hello")
            viewModelScope.launch {
                _state.value = _state.value.copy(isLoading = true, error = null)
                try {
                    val locations = getLocationsUseCase.invoke().first()
                    val uiLocations = locations.map { it.toPresentation() }
                    _state.value = _state.value.copy(locations = uiLocations, isLoading = false)
                } catch (e: Exception) {
                    _state.value = _state.value.copy(isLoading = false, error = e.message)
                }
            }
        }


    }