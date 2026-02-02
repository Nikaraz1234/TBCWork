package com.example.mycomposeapp.ui.screen.form

import com.example.mycomposeapp.MainDispatcherRule
import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Form
import com.example.mycomposeapp.domain.usecase.form.GetFormUseCase
import com.example.mycomposeapp.domain.usecase.validation.EmptyFieldUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FormViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK lateinit var getFormUseCase: GetFormUseCase

    private val emptyFieldUseCase = EmptyFieldUseCase()

    private lateinit var viewModel: FormViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = FormViewModel(getFormUseCase, emptyFieldUseCase)
    }

    @Test
    fun given_required_field_missing_when_register_clicked_then_snackbar_shows_required_error() = runTest {
        // GIVEN
        val flow = MutableSharedFlow<Resource<Form>>(replay = 0, extraBufferCapacity = 2)
        coEvery { getFormUseCase() } returns flow
        val domainForm = form_with_required_name_field()

        val effectDeferred = async { viewModel.sideEffect.first() }

        // WHEN
        viewModel.onEvent(FormContract.Event.LoadForm)
        flow.emit(Resource.Loading)
        flow.emit(Resource.Success(domainForm))
        advanceUntilIdle()

        viewModel.onEvent(FormContract.Event.RegisterClicked)
        advanceUntilIdle()

        // THEN
        val effect = effectDeferred.await() as FormContract.SideEffect.ShowSnackBar
        assertEquals("Name is required", effect.message)
    }

    @Test
    fun given_required_field_filled_when_register_clicked_then_snackbar_shows_registered() = runTest {
        // GIVEN
        val flow = MutableSharedFlow<Resource<Form>>(replay = 0, extraBufferCapacity = 2)
        coEvery { getFormUseCase() } returns flow
        val domainForm = form_with_required_name_field()

        val effectDeferred = async { viewModel.sideEffect.first() }

        // WHEN
        viewModel.onEvent(FormContract.Event.LoadForm)
        flow.emit(Resource.Loading)
        flow.emit(Resource.Success(domainForm))
        advanceUntilIdle()

        viewModel.onEvent(FormContract.Event.FieldValueChanged(fieldId = 1, value = "Nika"))
        advanceUntilIdle()

        viewModel.onEvent(FormContract.Event.RegisterClicked)
        advanceUntilIdle()

        // THEN
        val effect = effectDeferred.await() as FormContract.SideEffect.ShowSnackBar
        assertEquals("Registered", effect.message)
    }

    @Test
    fun given_loading_emitted_when_load_form_then_isLoading_true() = runTest {
        // GIVEN
        val flow = MutableSharedFlow<Resource<Form>>(replay = 0, extraBufferCapacity = 2)
        coEvery { getFormUseCase() } returns flow

        // WHEN
        viewModel.onEvent(FormContract.Event.LoadForm)
        flow.emit(Resource.Loading)
        advanceUntilIdle()

        // THEN
        assertTrue(viewModel.uiState.value.isLoading)
    }

    @Test
    fun given_success_emitted_when_load_form_then_loading_false_error_null_and_form_not_empty() = runTest {
        // GIVEN
        val flow = MutableSharedFlow<Resource<Form>>(replay = 0, extraBufferCapacity = 3)
        coEvery { getFormUseCase() } returns flow
        val domainForm = form_with_required_name_field()

        // WHEN
        viewModel.onEvent(FormContract.Event.LoadForm)
        flow.emit(Resource.Loading)
        flow.emit(Resource.Success(domainForm))
        advanceUntilIdle()

        // THEN
        assertFalse(viewModel.uiState.value.isLoading)
        assertNull(viewModel.uiState.value.error)
        assertTrue(viewModel.uiState.value.form.fields.isNotEmpty())
    }

    @Test
    fun given_error_emitted_when_load_form_then_error_set_loading_false_and_snackbar_shown() = runTest {
        // GIVEN
        val flow = MutableSharedFlow<Resource<Form>>(replay = 0, extraBufferCapacity = 3)
        coEvery { getFormUseCase() } returns flow
        val errorMessage = "Network error"

        val effectDeferred = async { viewModel.sideEffect.first() }

        // WHEN
        viewModel.onEvent(FormContract.Event.LoadForm)
        flow.emit(Resource.Loading)
        flow.emit(Resource.Error(errorMessage))
        advanceUntilIdle()

        // THEN
        assertEquals(errorMessage, viewModel.uiState.value.error)
        assertFalse(viewModel.uiState.value.isLoading)

        val effect = effectDeferred.await() as FormContract.SideEffect.ShowSnackBar
        assertEquals(errorMessage, effect.message)
    }

    private fun form_with_required_name_field(): Form =
        Form(
            fields = listOf(
                listOf(
                    Form.Field(
                        fieldId = 1,
                        hint = "Name",
                        fieldType = "text",
                        keyboard = null,
                        required = true,
                        isActive = true,
                        icon = "icon"
                    )
                )
            )
        )
}
