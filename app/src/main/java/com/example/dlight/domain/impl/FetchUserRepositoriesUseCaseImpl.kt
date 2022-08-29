package com.example.dlight.domain.impl

import com.example.dlight.data.Result
import com.example.dlight.data.localSource.model.Repository
import com.example.dlight.data.repository.UserRepository
import com.example.dlight.domain.FetchUserRepositoriesUseCase
import com.example.dlight.ui.UserRepositoryUiState

class FetchUserRepositoriesUseCaseImpl(private val userRepository: UserRepository) : FetchUserRepositoriesUseCase {
    override suspend fun invoke(userName: String) : List<Repository> {
        val repos= userRepository.fetchUserRepositories(userName)
             return when(repos){
                 is Result.Error -> emptyList()
                 is Result.Success -> repos.result as List<Repository>
             }
    }
}