package com.example.dlight.domain.repository

import com.example.dlight.domain.Result

interface UserRepository {
    suspend fun fetchUserByUserName(userName: String) : Result
}