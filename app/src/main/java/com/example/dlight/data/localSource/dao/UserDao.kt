package com.example.dlight.data.localSource.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dlight.data.localSource.model.User

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM User WHERE userName =:userName")
    suspend fun getUserProfile(userName: String): User
}