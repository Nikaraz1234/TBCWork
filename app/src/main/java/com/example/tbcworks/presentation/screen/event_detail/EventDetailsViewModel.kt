package com.example.tbcworks.presentation.screen.event_detail

import com.example.tbcworks.domain.model.event.Event
import com.example.tbcworks.domain.usecase.GetEventByIdUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.mapper.toPresentation
import com.example.tbcworks.presentation.screen.event_detail.mapper.toUiString
import com.example.tbcworks.presentation.screen.home.mapper.day
import com.example.tbcworks.presentation.screen.home.mapper.monthShort
import com.example.tbcworks.presentation.screen.home.mapper.registerDeadlineString
import com.example.tbcworks.presentation.screen.home.mapper.toTimeRange
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val getEventByIdUseCase: GetEventByIdUseCase
) : BaseViewModel<
        EventDetailsContract.State,
        EventDetailsContract.Effect,
        EventDetailsContract.Event
        >(
    initialState = EventDetailsContract.State()
) {

     fun onEvent(event: EventDetailsContract.Event) {
        when (event) {
            is EventDetailsContract.Event.Load -> loadEvent(event.eventId)
            EventDetailsContract.Event.BackClicked ->
                sendSideEffect(EventDetailsContract.Effect.NavigateBack)

            EventDetailsContract.Event.RegisterClicked -> handleRegisterClick()

        }
    }

    private fun loadEvent(eventId: String) {
        handleResponse(
            apiCall = { getEventByIdUseCase(eventId) },

            onLoading = {
                setState { copy(isLoading = true, errorMessage = null) }
            },

            onSuccess = { event ->
                setState {
                    copy(
                        isLoading = false,

                        title = event.title,
                        bannerUrl = event.imgUrl,
                        eventDate =
                            "${event.date.day()} ${event.date.monthShort()}, ${event.date.startDate.year}",

                        eventTime =
                            event.date.toTimeRange(),
                        location = event.location.address.toUiString(),
                        capacity = "${event.capacity} Seats",
                        isRegistrationOpen = event.registrationStatus == Event.RegistrationStatus.OPEN,
                        registerCloseText =
                            "Registration closes on ${event.date.registerDeadlineString()}.",
                        aboutDescription = event.description,
                        agenda = event.agenda.map { it.toPresentation() },
                        speakers = event.speakers.map { it.toPresentation() }
                    )
                }
            },

            onError = { message ->
                setState {
                    copy(
                        isLoading = false,
                        errorMessage = message
                    )
                }
                sendSideEffect(
                    EventDetailsContract.Effect.ShowError(message)
                )
            }
        )
    }

    private fun handleRegisterClick() {
        val state = uiState.value

        if (!state.isRegistrationOpen) {
            sendSideEffect(
                EventDetailsContract.Effect.ShowMessage(
                    "Registration is closed"
                )
            )
            return
        }

        sendSideEffect(
            EventDetailsContract.Effect.ShowMessage(
                "Registration is open. Please register via official channel."
            )
        )
    }

}
