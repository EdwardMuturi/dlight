package com.example.dlight.data.localSource.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dlight.data.localSource.model.Followers

@Dao
interface FollowersDao : BaseDao<Followers> {

    @Query("SELECT * FROM Followers WHERE login =:userLogin")
    suspend fun getFollowersByGithubUsername(userLogin: String): List<Followers>
}