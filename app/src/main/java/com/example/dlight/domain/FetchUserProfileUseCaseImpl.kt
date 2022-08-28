package com.example.dlight.domain

import com.example.dlight.data.Result
import com.example.dlight.data.repository.UserRepository

class FetchUserProfileUseCaseImpl(private val userRepository: UserRepository): FetchUserProfileUseCase {
    override suspend fun invoke(userName: String): Result {
        return userRepository.fetchUserByUserName(userName)
    }
}