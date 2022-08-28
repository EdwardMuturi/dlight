package com.example.dlight.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dlight.data.Result
import com.example.dlight.data.localSource.model.User
import com.example.dlight.domain.FetchUserProfileUseCase
import com.example.dlight.ui.SearchUserUiState
import com.example.dlight.ui.UserProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel(private val fetchUserProfileUseCase: FetchUserProfileUseCase) : ViewModel() {

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

                    val userProfileUiState= UserProfileUiState(
                        avatar= user.avatarUrl,
                       name= user.name,
                        userName= user.userName,
                        blog = user.url
                    )
                    Timber.e(user.toString())
                    _searchUserUiState.update {
                        it.copy(userProfileUiState= userProfileUiState, isLoading = false)
                    }
                }
            }
        }
    }
}

