package com.example.dlight.data.localSource.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dlight.data.localSource.model.Followings

@Dao
interface FollowingDao : BaseDao<Followings> {

    @Query("SELECT * FROM Followings WHERE login =:userLogin")
    suspend fun getFollowingByGithubUsername(userLogin: String): List<Followings>
}