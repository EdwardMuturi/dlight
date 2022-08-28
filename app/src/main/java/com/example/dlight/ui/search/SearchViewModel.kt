package com.example.dlight.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dlight.data.Result
import com.example.dlight.data.localSource.model.User
import com.example.dlight.domain.FetchUserProfileUseCase
import com.example.dlight.domain.FetchUserRepositoriesUseCase
import com.example.dlight.ui.SearchUserUiState
import com.example.dlight.ui.UserProfileUiState
import com.example.dlight.ui.UserRepositoryUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val fetchUserRepositoriesUseCase: FetchUserRepositoriesUseCase
) : ViewModel() {

    private var _searchUserUiState = MutableStateFlow(SearchUserUiState())
    val searchUserUiState get() = _searchUserUiState.asStateFlow()

    fun searchUserProfile(userName: String) {
        viewModelScope.launch {
            _searchUserUiState.update { it.copy(isLoading = true) }
            val userResult = fetchUserProfileUseCase(userName)
            when (userResult) {
                is Result.Error -> _searchUserUiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "User record not found in DB!"
                    )
                }
                is Result.Success -> {
                    val user = userResult.result as User
                    val userProfileUiState = UserProfileUiState(
                        user.avatarUrl,
                        user.name,
                        user.userName,
                        user.bio.split("\n").toString().removeSurrounding("[", "]"),
                        user.company,
                        user.blog,
                        user.followers.toString(),
                        user.following.toString(),
                        user.repositories.toString()
                    )
                    _searchUserUiState.update {
                        it.copy(userProfileUiState = userProfileUiState, isLoading = false)
                    }
                }
            }
        }
    }

    fun fetchUserRepos(userName: String) {
        viewModelScope.launch {
            val repos = fetchUserRepositoriesUseCase(userName).map {
                UserRepositoryUiState(
                    it.name,
                    it.description,
                    it.stargazersCount.toString(),
                    it.watchersCount.toString()
                )
            }
            _searchUserUiState.update { it.copy(userRepositories = repos) }
        }
    }
}

