package com.example.dlight.ui

import androidx.compose.runtime.Composable
import com.example.dlight.data.localSource.model.User
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

data class SearchUserUiState(
    val userProfileUiState: UserProfileUiState= UserProfileUiState(),
    val isLoading: Boolean = false,
    val errorMessage: String = String()
)

data class UserProfileUiState(
    val avatar: String= String(),
    val name: String= String(),
    val userName: String= String(),
    val bio: String= String(),
    val organization: String= String(),
    val blog: String= String(),
    val followers: String= String(),
    val following: String= String(),
    val repositories: String= String(),
)

@Composable
fun DlightSwipeRefresh(isRefreshingState: Boolean, onRefreshData : () -> Unit, content: @Composable () -> Unit) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshingState),
        onRefresh = { onRefreshData() }) {
        content()
    }
}