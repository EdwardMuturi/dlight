package com.example.dlight.data.repository.impl

import com.example.dlight.data.localSource.dao.UserDao
import com.example.dlight.data.Result
import com.example.dlight.data.localSource.asUser
import com.example.dlight.data.localSource.dao.RepositoryDao
import com.example.dlight.data.remoteSource.GitHubApi
import com.example.dlight.data.remoteSource.model.asRepository
import com.example.dlight.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val repositoryDao: RepositoryDao,
                         private val gitHubApi: GitHubApi) : UserRepository {
    override suspend fun fetchUserByUserName(userName: String): Result {
        val localUser= userDao.getUserProfile(userName)
        if(localUser == null)
            return fetchUserFromRemote(userName)
        return Result.Success(localUser)
    }

    private suspend fun fetchUserFromRemote(userName: String): Result {
        val netRes = gitHubApi.getUser(userName)
        return try {
            when (netRes.isSuccessful) {
                true -> {
                    val user = netRes.body()?.asUser() ?: throw NullPointerException("User record not found!")
                    userDao.insert(user)
                    Result.Success(user)
                }
                false -> Result.Error(netRes.message())
            }
        } catch (e: Exception) {
            Timber.e(e.message)
            Result.Error(e.localizedMessage)
        }
    }

    override suspend fun fetchUserRepositories(userName: String): Result {
        val localRepos= repositoryDao.getGithubRepoByUsername(userName)
        if (localRepos.isEmpty())
            return fetchUserRepositoriesRemote(userName)
        return Result.Success(localRepos)
    }

    private suspend fun fetchUserRepositoriesRemote(userName: String) : Result {
        val repos = gitHubApi.getUserRepositories(userName)
        return try {
            when (repos.isSuccessful) {
                true -> {
                    val repos = repos.body()?.map { it.asRepository() }
                        ?: throw NullPointerException("User repos not found!")
                    repositoryDao.insert(repos)
                    Result.Success(repos)
                }
                false -> Result.Error(repos.message())
            }
        } catch (e: Exception) {
            Timber.e(e.message)
            Result.Error(e.localizedMessage)
        }
    }

    override suspend fun fetchUserFollowers(userName: String): Result {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUserFollowing(userName: String): Result {
        TODO("Not yet implemented")
    }
}