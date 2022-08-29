package com.example.dlight.data.SampleData

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.dlight.data.localSource.Database
import com.example.dlight.data.localSource.dao.FollowersDao
import com.example.dlight.data.localSource.dao.FollowingDao
import com.example.dlight.data.localSource.dao.RepositoryDao
import com.example.dlight.data.localSource.dao.UserDao
import org.junit.After
import org.junit.Before
import java.io.IOException

internal open class BaseTest : BaseKoinTest() {

    // database and dao
    private lateinit var database: Database
    protected lateinit var userDao: UserDao
    protected lateinit var followersDao: FollowersDao
    protected lateinit var followingDao: FollowingDao
    protected lateinit var repositoryDao: RepositoryDao

    @Before
    open fun setup() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room
            .inMemoryDatabaseBuilder(context, Database::class.java)
            .allowMainThreadQueries()
            .build()

        userDao = database.userDao()
        followersDao = database.followersDao()
        followersDao = database.followersDao()
        repositoryDao = database.repositoryDao()
        followingDao = database.followingDao()
    }

    @After
    @Throws(IOException::class)
    open fun tearDown() {
        database.close()
    }
}