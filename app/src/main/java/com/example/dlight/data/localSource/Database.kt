package com.example.dlight.data.localSource

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dlight.data.localSource.dao.FollowersDao
import com.example.dlight.data.localSource.dao.FollowingDao
import com.example.dlight.data.localSource.dao.RepositoryDao
import com.example.dlight.data.localSource.dao.UserDao
import com.example.dlight.data.localSource.model.Followers
import com.example.dlight.data.localSource.model.Followings
import com.example.dlight.data.localSource.model.Repository
import com.example.dlight.data.localSource.model.User
import com.example.dlight.data.localSource.utils.DateConverter

@androidx.room.Database(
    entities = [User::class, Followers::class, Repository::class, Followings::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(DateConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun followersDao(): FollowersDao
    abstract fun followingDao(): FollowingDao
    abstract fun repositoryDao(): RepositoryDao
}