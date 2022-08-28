package com.example.dlight.domain.impl

import com.example.dlight.data.Result
import com.example.dlight.data.repository.UserRepository
import com.example.dlight.domain.FetchUserProfileUseCase

class FetchUserProfileUseCaseImpl(private val userRepository: UserRepository):
    FetchUserProfileUseCase {
    override suspend fun invoke(userName: String): Result {
        return userRepository.fetchUserByUserName(userName)
    }
}