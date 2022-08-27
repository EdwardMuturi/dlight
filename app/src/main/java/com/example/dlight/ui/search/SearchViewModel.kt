package com.example.dlight.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dlight.data.Result
import com.example.dlight.data.localSource.model.User
import com.example.dlight.domain.FetchUserProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

data class SearchUserUiState(val user: User? = null, val isLoading: Boolean= false, val errorMessage: String= "")
class SearchViewModel(private val fetchUserProfileUseCase: FetchUserProfileUseCase) : ViewModel() {

    private var _searchUserUiState = MutableStateFlow(SearchUserUiState())
    val searchUserUiState get() = _searchUserUiState.asStateFlow()

    fun searchUserProfile(userName: String){
        viewModelScope.launch {
            _searchUserUiState.update { it.copy(isLoading = true) }
            val userResult= fetchUserProfileUseCase(userName)
            when(userResult){
                is Result.Error ->  _searchUserUiState.update { it.copy(isLoading = false, errorMessage = "User record not found in DB!") }
                is Result.Success<*> ->  _searchUserUiState.update { it.copy(user= userResult.result as User, isLoading = false) }
            }
        }
    }
}