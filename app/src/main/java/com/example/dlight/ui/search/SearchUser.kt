package com.example.dlight.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import org.koin.androidx.compose.viewModel

@Composable
fun SearchUserScreen() {
    val searchViewModel: SearchViewModel by viewModel()
    val uiState= searchViewModel.searchUserUiState.collectAsState()
    var t by remember { mutableStateOf(TextFieldValue("")) }

    SearchUserScreenContent(
        t,
        uiState,
        onUpdateText = { t = it },
        onSearchUser = { searchViewModel.searchUserProfile(it) }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchUserScreenContent(
    textValue: TextFieldValue,
    searchUserUiState: State<SearchUserUiState>,
    onUpdateText: (TextFieldValue) -> Unit,
    onSearchUser: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(Modifier.fillMaxSize()) {
        TextField(
            value = textValue,
            onValueChange = { onUpdateText(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search user") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            },
                onSearch = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onSearchUser(textValue.text)
                })
        )

        if (!searchUserUiState.value.isLoading)
        Text(text = searchUserUiState.value.user?.userName ?: searchUserUiState.value.errorMessage)
    }
}