package com.example.dlight.data.localSource.dao

import androidx.room.Query
import com.example.dlight.data.localSource.model.Followings
import com.example.dlight.data.localSource.model.Repository

interface RepositoryDao: BaseDao<Repository> {

    @Query("SELECT * FROM Repository WHERE userLogin =:userLogin")
    suspend fun getFollowingByGithubUsername(userLogin: String): List<Followings>
}