package com.example.dlight.data.localSource.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dlight.data.localSource.model.Repository

@Dao
interface RepositoryDao: BaseDao<Repository> {
    @Query("SELECT * FROM Repository WHERE login =:userName")
    suspend fun getGithubRepoByUsername(userName: String): List<Repository>
}