package com.example.mycomposeapp.ui.screen.form

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mycomposeapp.R
import com.example.mycomposeapp.ui.components.Loader
import com.example.mycomposeapp.ui.components.NetworkImage

import com.example.mycomposeapp.ui.screen.form.model.FormUi
import com.example.mycomposeapp.ui.theme.Black
import com.example.mycomposeapp.ui.theme.MyComposeAppTheme
import com.example.mycomposeapp.ui.theme.MyTheme
import com.example.mycomposeapp.ui.theme.Radius
import com.example.mycomposeapp.ui.theme.Spacer
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FormScreen(
    snackBarHostState: SnackbarHostState,
    viewModel: FormViewModel = hiltViewModel()
){
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(FormContract.Event.LoadForm)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { effect ->
            when (effect) {
                is FormContract.SideEffect.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = effect.message,
                    )
                    print(effect.message)
                }
            }
        }
    }
    FormContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FormContent(
    state: FormContract.State,
    onEvent: (FormContract.Event) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacer.spacer20,
                        vertical = Spacer.spacer16
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.title),
                    color = MyTheme.colorScheme.onSurface,
                    style = MyTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(R.string.register),
                    color = MyTheme.colorScheme.onSurfaceVariant,
                    style = MyTheme.typography.titleLarge
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spacer.spacer16),
                contentPadding = PaddingValues(
                    horizontal = Spacer.spacer20,
                    vertical = Spacer.spacer16
                )
            ) {
                items(
                    items = state.form.fields,
                    key = { section ->
                        section.firstOrNull()?.fieldId ?: section.hashCode()
                    }
                ) { section ->
                    FormSectionCard(
                        fields = section,
                        fieldValues = state.fieldValues,
                        onEvent = onEvent
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacer.spacer20, vertical = Spacer.spacer16),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onEvent(FormContract.Event.RegisterClicked) },
                    shape = Radius.radius16,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MyTheme.colorScheme.primary,
                        contentColor = MyTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.register),
                        style = MyTheme.typography.labelNormal
                    )
                }
            }

        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Loader()
            }
        }
    }
}


@Composable
private fun FormSectionCard(
    fields: List<FormUi.FieldUi>,
    fieldValues: Map<Int, String>,
    onEvent: (FormContract.Event) -> Unit
) {
    Card(
        shape = Radius.radius12,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MyTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            fields.forEach{ field ->
                FieldRow(
                    field = field,
                    fieldValues = fieldValues,
                    onEvent = onEvent)
            }
        }
    }
}
@Composable
private fun FieldRow(field: FormUi.FieldUi,
                     fieldValues: Map<Int, String>,
                     onEvent: (FormContract.Event) -> Unit
) {
    when (field.fieldType.lowercase()) {
        stringResource(R.string.input) -> InputFieldRow(
            field,
            fieldValues,
            onEvent)
        stringResource(R.string.chooser) -> ChooserRow(field)
        else -> Unit
    }
}

@Composable
private fun InputFieldRow(
    field: FormUi.FieldUi,
    fieldValues: Map<Int, String>,
    onEvent: (FormContract.Event) -> Unit) {
    val value = fieldValues[field.fieldId].orEmpty()
    TextField(
        value = value,
        onValueChange = { newText ->
            onEvent(FormContract.Event.FieldValueChanged(field.fieldId, newText))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        placeholder = { Text(field.hint) },
        singleLine = true,
        trailingIcon = {
            NetworkImage(
                imageUrl = field.icon,
                modifier = Modifier.size(24.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = when (field.keyboard?.lowercase()) {
                stringResource(R.string.number) -> KeyboardType.Number
                stringResource(R.string.phone) -> KeyboardType.Phone
                stringResource(R.string.email) -> KeyboardType.Email
                else -> KeyboardType.Text
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MyTheme.colorScheme.surface,
            unfocusedContainerColor = MyTheme.colorScheme.surface,
            disabledContainerColor = MyTheme.colorScheme.surface,

            focusedTextColor = MyTheme.colorScheme.onSurface,
            unfocusedTextColor = MyTheme.colorScheme.onSurface,
            disabledTextColor = MyTheme.colorScheme.onSurface.copy(alpha = 0.38f),

            ),

        )
}

@Composable
private fun ChooserRow(field: FormUi.FieldUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {  }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = field.hint,
            style = MyTheme.typography.labelNormal,
            color = MyTheme.colorScheme.onSurface)
        NetworkImage(
            imageUrl = field.icon,
            modifier = Modifier.size(24.dp)
        )

    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun FormContentPreview() {
    MyComposeAppTheme {
        FormContent(
            state = FormContract.State(
                isLoading = false,
                error = null,
                form = FormUi.empty()
            ),
            onEvent = {}
        )
    }
}