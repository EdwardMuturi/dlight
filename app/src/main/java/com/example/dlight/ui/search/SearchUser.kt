package com.example.dlight.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.dlight.R
import com.example.dlight.ui.DlightSwipeRefresh
import com.example.dlight.ui.SearchUserUiState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.viewModel

@Composable
fun SearchUserScreen() {
    val searchViewModel: SearchViewModel by viewModel()
    val uiState by searchViewModel.searchUserUiState.collectAsState()
    var searchValue by remember { mutableStateOf(TextFieldValue("")) }

    SearchUserScreenContent(
        searchValue,
        uiState,
        onUpdateText = { searchValue = it }
    ) { searchViewModel.searchUserProfile(it) }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchUserScreenContent(
    textValue: TextFieldValue,
    searchUserUiState: SearchUserUiState,
    onUpdateText: (TextFieldValue) -> Unit,
    onSearchUser: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    DlightSwipeRefresh(
        isRefreshingState = searchUserUiState.isLoading,
        onRefreshData = { onSearchUser(textValue.text) }) {
        Column(Modifier.fillMaxSize()) {
            TextField(
                value = textValue,
                onValueChange = { onUpdateText(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.search_user)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                    onSearch = {
                        onSearchUser(textValue.text)
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    })
            )

            Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)){
                Image(
                    painter = rememberImagePainter(searchUserUiState.userProfileUiState.avatar),
                    contentDescription = "Profile photo",
                    modifier = Modifier.size(60.dp).clip(RoundedCornerShape(4.dp))
                )
                Column(modifier = Modifier.padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween){
                    Text(
                        text = searchUserUiState.userProfileUiState.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = searchUserUiState.userProfileUiState.userName,
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp
                    )
                }
            }
            Text(text = searchUserUiState.userProfileUiState.organization)

        }
    }

}