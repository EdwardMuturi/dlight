
package com.example.dlight.data.SampleData

import com.example.dlight.di.dlightModules
import org.junit.Rule
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

abstract class BaseKoinTest : KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create{
        printLogger()
        modules(dlightModules)
    }
}