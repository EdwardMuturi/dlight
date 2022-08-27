package com.example.dlight.data.repository

import com.example.dlight.domain.Result
import com.example.dlight.domain.repository.UserRepository

class UserRepositoryImpl() : UserRepository {
    override suspend fun fetchUserByUserName(userName: String): Result {
        TODO("Not yet implemented")
    }
}