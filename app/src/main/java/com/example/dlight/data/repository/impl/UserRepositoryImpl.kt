package com.example.dlight.data.repository.impl

import com.example.dlight.data.localSource.dao.UserDao
import com.example.dlight.data.Result
import com.example.dlight.data.localSource.asUser
import com.example.dlight.data.localSource.dao.FollowersDao
import com.example.dlight.data.localSource.dao.FollowingDao
import com.example.dlight.data.localSource.dao.RepositoryDao
import com.example.dlight.data.remoteSource.GitHubApi
import com.example.dlight.data.remoteSource.model.asFollower
import com.example.dlight.data.remoteSource.model.asFollowings
import com.example.dlight.data.remoteSource.model.asRepository
import com.example.dlight.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val repositoryDao: RepositoryDao,
    private val followingDao: FollowingDao,
    private val followersDao: FollowersDao,
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
                    val repoBody = repos.body()?.map { it.asRepository() }
                        ?: throw NullPointerException("User repos not found!")
                    repositoryDao.insert(repoBody)
                    Result.Success(repoBody)
                }
                false -> Result.Error(repos.message())
            }
        } catch (e: Exception) {
            Timber.e(e.message)
            Result.Error(e.localizedMessage)
        }
    }

    override suspend fun fetchUserFollowers(userName: String): Result {
        val followers= followersDao.getFollowersByGithubUsername(userName)
        if (followers.isEmpty())
            return fetchUserFollowersRemote(userName)
        return Result.Success(followers)
    }

    private suspend fun fetchUserFollowersRemote(userName: String) : Result {
        val followers = gitHubApi.getUserFollowers(userName)
        return try {
            when (followers.isSuccessful) {
                true -> {
                    val repos = followers.body()?.map { it.asFollower() }
                        ?: throw NullPointerException("User repos not found!")
                    followersDao.insert(repos)
                    Result.Success(repos)
                }
                false -> Result.Error(followers.message())
            }
        } catch (e: Exception) {
            Timber.e(e.message)
            Result.Error(e.localizedMessage)
        }
    }

    override suspend fun fetchUserFollowing(userName: String): Result {
        val following= followingDao.getFollowingByGithubUsername(userName)
        if (following.isEmpty())
            return fetchUserFollowingRemote(userName)
        return Result.Success(following)
    }

    private suspend fun fetchUserFollowingRemote(userName: String) : Result {
        val following = gitHubApi.getUserFollowing(userName)
        return try {
            when (following.isSuccessful) {
                true -> {
                    val repos = following.body()?.map { it.asFollowings() }
                        ?: throw NullPointerException("User repos not found!")
                    followingDao.insert(repos)
                    Result.Success(repos)
                }
                false -> Result.Error(following.message())
            }
        } catch (e: Exception) {
            Timber.e(e.message)
            Result.Error(e.localizedMessage)
        }
    }
}