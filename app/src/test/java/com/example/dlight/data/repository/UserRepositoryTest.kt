package com.example.dlight.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dlight.data.Result
import com.example.dlight.data.SampleData.BaseTest
import com.example.dlight.data.localSource.dao.UserDao
import com.example.dlight.data.localSource.model.User
import com.example.dlight.data.repository.impl.UserRepositoryImpl
import com.example.dlight.data.testUser
import com.example.dlight.data.userName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Config.OLDEST_SDK], manifest = Config.NONE)
internal class UserRepositoryTest : KoinTest {
    private val userRepository by inject<UserRepository>()
//    private val userDao= FakeUserDao()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules( module {
            single <UserDao> { FakeUserDao() }
            single <UserRepository>{ UserRepositoryImpl(get()) }
        }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `valid user name returns profile`() = runTest {
        val result = userRepository.fetchUserByUserName(userName) as Result.Success<User>
        assertEquals(testUser[0].userName, result.result.userName)
    }


    @Test
    fun `return error message when user record is not available in local`() {
    }
}

class FakeUserDao : UserDao {
    override suspend fun getUserProfile(userName: String): User {
        return testUser.first()
    }

    override suspend fun insert(item: User) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(vararg items: User) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(items: List<User>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: User): Int {
        TODO("Not yet implemented")
    }

    override suspend fun update(items: List<User>): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: User) {
        TODO("Not yet implemented")
    }
}