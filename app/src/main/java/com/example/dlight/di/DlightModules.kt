package com.example.dlight.di

import androidx.room.Room
import com.example.dlight.data.localSource.Database
import com.example.dlight.data.repository.UserRepositoryImpl
import com.example.dlight.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val repoModule= module {
    single<UserRepository> { UserRepositoryImpl() }
}


private val databaseModule: Module = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            "dlight-db"
        ).build()
    }
}
