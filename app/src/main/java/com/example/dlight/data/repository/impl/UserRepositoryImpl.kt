package com.example.dlight.data.repository.impl

import com.example.dlight.data.localSource.dao.UserDao
import com.example.dlight.data.Result
import com.example.dlight.data.localSource.asUser
import com.example.dlight.data.remoteSource.GitHubApi
import com.example.dlight.data.repository.UserRepository
import timber.log.Timber

class UserRepositoryImpl(private val userDao: UserDao, private val gitHubApi: GitHubApi) : UserRepository {
    override suspend fun fetchUserByUserName(userName: String): Result {
        val localUser= userDao.getUserProfile(userName)

        if(localUser == null) {
            val netRes= gitHubApi.getUser(userName).also { Timber.e("LocalData $it") }
            return try {
                when(netRes.isSuccessful){
                    true -> {
                        val user= netRes.body()?.asUser() ?: throw NullPointerException("User record not found!")
                        userDao.insert(user)
                        Result.Success(user)
                    }
                    false -> Result.Error(netRes.message())
                }
            }catch (e: Exception) {
                Timber.e(e.message)
                Result.Error(e.localizedMessage)
            }
        }

        return Result.Success(localUser)
    }
}