package com.example.dlight.data.repository.impl

import com.example.dlight.data.localSource.dao.UserDao
import com.example.dlight.data.Result
import com.example.dlight.data.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun fetchUserByUserName(userName: String): Result {
        val localUser= userDao.getUserProfile(userName)
        if(localUser == null)
            return Result.Error("User record not found in DB!")

        return Result.Success(localUser)

    }
}