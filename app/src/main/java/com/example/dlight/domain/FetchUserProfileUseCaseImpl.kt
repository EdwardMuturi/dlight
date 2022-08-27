package com.example.dlight.domain

import com.example.dlight.data.Result
import com.example.dlight.data.localSource.model.User
import com.example.dlight.data.repository.UserRepository
import timber.log.Timber

class FetchUserProfileUseCaseImpl(private val userRepository: UserRepository): FetchUserProfileUseCase {
    override suspend fun invoke(userName: String): Result {
        return userRepository.fetchUserByUserName(userName)
//        return when(result){
//            is Result.Error -> null
//            is Result.Success<*> -> result.result as User
//        }
    }
}