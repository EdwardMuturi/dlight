package com.example.dlight.di

import androidx.room.Room
import com.example.dlight.data.localSource.Database
import com.example.dlight.data.remoteSource.GitHubApi
import com.example.dlight.data.repository.impl.UserRepositoryImpl
import com.example.dlight.data.repository.UserRepository
import com.example.dlight.domain.FetchUserProfileUseCase
import com.example.dlight.domain.FetchUserProfileUseCaseImpl
import com.example.dlight.ui.search.SearchViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private fun okHttpBuilder() = OkHttpClient.Builder().build()
private const val BASE_URL= "https://api.github.com/"

private val repoModule: Module = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
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

val networkModule = module {
    single { okHttpBuilder() }

    single {
        Retrofit.Builder()
            .callFactory(OkHttpClient.Builder().build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(GitHubApi::class.java) }
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
    networkModule,
    daoModule,
    repoModule,
    useCaseModule,
    viewModelModule
)
