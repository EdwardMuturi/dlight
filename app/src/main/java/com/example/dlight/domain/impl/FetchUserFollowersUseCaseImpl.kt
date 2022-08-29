package com.example.dlight.domain.impl

import com.example.dlight.data.Result
import com.example.dlight.data.localSource.model.Followers
import com.example.dlight.data.repository.UserRepository
import com.example.dlight.domain.FetchUserFollowersUseCase

class FetchUserFollowersUseCaseImpl(private val userRepository: UserRepository) : FetchUserFollowersUseCase {
    override suspend fun invoke(userName: String): List<Followers> {
        val result= userRepository.fetchUserFollowers(userName)
        return when(result){
            is Result.Error -> emptyList()
            is Result.Success -> result.result as List<Followers>
        }
    }
}