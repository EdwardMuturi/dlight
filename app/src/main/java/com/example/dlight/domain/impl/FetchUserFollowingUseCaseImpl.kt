package com.example.dlight.domain.impl

import com.example.dlight.data.Result
import com.example.dlight.data.localSource.model.Followers
import com.example.dlight.data.localSource.model.Followings
import com.example.dlight.data.repository.UserRepository
import com.example.dlight.domain.FetchUserFollowingUseCase

class FetchUserFollowingUseCaseImpl(private val userRepository: UserRepository) : FetchUserFollowingUseCase {
    override suspend fun invoke(userName: String): List<Followings> {
        val result= userRepository.fetchUserFollowing(userName)
        return when(result) {
            is Result.Error -> emptyList()
            is Result.Success -> result.result as List<Followings>
        }
    }
}