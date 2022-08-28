package com.example.dlight.data.repository

import com.example.dlight.data.Result

interface UserRepository {
    suspend fun fetchUserByUserName(userName: String) : Result
    suspend fun fetchUserRepositories(userName: String) : Result
    suspend fun fetchUserFollowers(userName: String) : Result
    suspend fun fetchUserFollowing(userName: String) : Result
}