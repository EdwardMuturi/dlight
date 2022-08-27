package com.example.dlight.di

import androidx.room.Room
import com.example.dlight.data.localSource.Database
import com.example.dlight.data.repository.impl.UserRepositoryImpl
import com.example.dlight.data.repository.UserRepository
import com.example.dlight.domain.FetchUserProfileUseCase
import com.example.dlight.domain.FetchUserProfileUseCaseImpl
import com.example.dlight.ui.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val repoModule: Module = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
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

private val daoModule: Module = module {
    single { get<Database>().userDao() }
    single { get<Database>().followersDao() }
    single { get<Database>().followingDao() }
    single { get<Database>().repositoryDao() }
}

private val useCaseModule : Module = module {
    single <FetchUserProfileUseCase> { FetchUserProfileUseCaseImpl(get()) }
}

private val viewModelModule: Module = module {
    viewModel { SearchViewModel(get()) }
}

val dlightModules: List<Module> = listOf(
    databaseModule,
    daoModule,
    repoModule,
    useCaseModule,
    viewModelModule
)
