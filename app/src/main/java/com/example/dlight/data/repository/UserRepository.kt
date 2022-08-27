package com.example.dlight.data.repository

import com.example.dlight.data.Result

interface UserRepository {
    suspend fun fetchUserByUserName(userName: String) : Result
}