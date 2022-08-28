package com.example.dlight.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
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
import com.example.dlight.ui.FollowersAndFollowingUiState
import com.example.dlight.ui.SearchUserUiState
import org.koin.androidx.compose.viewModel
import timber.log.Timber

@Composable
fun SearchUserScreen() {
    val searchViewModel: SearchViewModel by viewModel()
    val uiState by searchViewModel.searchUserUiState.collectAsState()
    var searchValue by remember { mutableStateOf(TextFieldValue("")) }

    SearchUserScreenContent(
        searchValue,
        uiState,
        onUpdateText = { searchValue = it }
    ) { searchViewModel.searchUserProfile(it)
        searchViewModel.fetchUserRepos(it)
        searchViewModel.fetchUserFollowers(it)
        searchViewModel.fetchUserFollowing(it)
    }
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
    val scrollState = rememberScrollState(0)

    DlightSwipeRefresh(
        isRefreshingState = searchUserUiState.isLoading,
        onRefreshData = { onSearchUser(textValue.text) }) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            SearchBar(textValue, onUpdateText, focusManager, onSearchUser, keyboardController)
            ProfileInfo(searchUserUiState)
            RepoAndOrganizationCount(searchUserUiState)
            AccountsInfo(searchUserUiState)
        }
    }

}

@Composable
private fun RepoAndOrganizationCount(searchUserUiState: SearchUserUiState) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        RepoAndOrganizationCountItem(
            R.string.text_repositories,
            searchUserUiState.userProfileUiState.repositories
        )
        Divider()
        RepoAndOrganizationCountItem(
            R.string.text_organizations,
            searchUserUiState.userProfileUiState.organization
        )
    }
}

@Composable
private fun RepoAndOrganizationCountItem(stringRes: Int, text: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 15.dp)
    ) {
        Text(
            text = stringResource(stringRes), modifier = Modifier.align(
                Alignment.CenterStart
            )
        )
        Text(
            text = text, modifier = Modifier.align(
                Alignment.CenterEnd
            )
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchBar(
    textValue: TextFieldValue,
    onUpdateText: (TextFieldValue) -> Unit,
    focusManager: FocusManager,
    onSearchUser: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
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
}

@Composable
private fun ProfileInfo(searchUserUiState: SearchUserUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        UserHeader(searchUserUiState.userProfileUiState.avatar, searchUserUiState.userProfileUiState.name, searchUserUiState.userProfileUiState.userName)
        DlightText(
            text = searchUserUiState.userProfileUiState.bio,
            modifier = Modifier.fillMaxWidth()
        )
        if (searchUserUiState.userProfileUiState.organization.isNotEmpty()
            || searchUserUiState.userProfileUiState.blog.isNotEmpty()
        ) {
            ProfileUiItem(
                text = searchUserUiState.userProfileUiState.organization,
                Icons.Default.Person
            )
            ProfileUiItem(
                text = searchUserUiState.userProfileUiState.blog,
                Icons.Default.AccountBox
            )
        }
        ProfileUiItem(text = searchUserUiState.userProfileUiState.followers, Icons.Default.Person)
        ProfileUiItem(text = searchUserUiState.userProfileUiState.following, Icons.Default.Share)
    }
}

@Composable
private fun UserHeader(avatar: String, name: String, userName: String) {
    Row(modifier = Modifier.padding(bottom = 20.dp)) {
        Image(
            painter = rememberImagePainter(avatar),
            contentDescription = "Profile photo",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
            )
            Text(
                text = userName,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ProfileUiItem(text: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(imageVector = icon, contentDescription = icon.name, modifier = Modifier.size(20.dp))
        Text(
            text = text, modifier = modifier
                .fillMaxWidth()
                .padding(start = 10.dp, bottom = 10.dp)
        )
    }
}

@Composable
fun AccountsInfo(searchUserUiState: SearchUserUiState) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
        AccountsInfoItem("Following", searchUserUiState.followings, searchUserUiState.userProfileUiState.following)
        AccountsInfoItem("Followers", searchUserUiState.followings, searchUserUiState.userProfileUiState.followers)
    }
}

@Composable
private fun AccountsInfoItem(
    title: String,
    followersAndFollowingUiState: List<FollowersAndFollowingUiState>,
    count: String) {
    Column(modifier = Modifier.padding(start = 20.dp, top = 15.dp)){
        ProfileUiItem(
            text = "$title ($count)",
            icon = Icons.Default.Person,
        )
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
    ) {
        items(followersAndFollowingUiState) {
            Card(
                Modifier
                    .size(width = 300.dp, height = 150.dp)
                    .padding(start = 10.dp)) {
                Column(Modifier.fillMaxWidth().padding(10.dp)) {
                    UserHeader(it.avatar, it.name, it.userName)
                    DlightText(text = it.bio)
                }
            }
        }
    }
}

@Composable
fun DlightText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    )
}

