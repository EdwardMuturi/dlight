package com.example.dlight.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dlight.data.SampleData.BaseTest
import com.example.dlight.data.testUser
import com.example.dlight.data.userName
import com.google.common.base.CharMatcher.`is`
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Config.OLDEST_SDK], manifest = Config.NONE)
internal class UserDaoTest : BaseTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `test inserting and retrieving users`() = runTest {
        userDao.insert(testUser)
        val user = userDao.getUserProfile(userName)
        assertEquals(user.name, testUser[0].name)
    }
}