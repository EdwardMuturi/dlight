package com.example.dlight.di

import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.dlight.BuildConfig
import com.example.dlight.data.localSource.Database
import com.example.dlight.data.remoteSource.AuthInterceptor
import com.example.dlight.data.remoteSource.GitHubApi
import com.example.dlight.data.repository.impl.UserRepositoryImpl
import com.example.dlight.data.repository.UserRepository
import com.example.dlight.domain.FetchUserProfileUseCase
import com.example.dlight.domain.FetchUserRepositoriesUseCase
import com.example.dlight.domain.impl.FetchUserProfileUseCaseImpl
import com.example.dlight.domain.impl.FetchUserRepositoriesUseCaseImpl
import com.example.dlight.ui.search.SearchViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL= "https://api.github.com/"

private val repoModule: Module = module {
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
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

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = when (BuildConfig.BUILD_TYPE) {
            "release" -> HttpLoggingInterceptor.Level.NONE
            else -> HttpLoggingInterceptor.Level.BODY
        }

        val chuckerInterceptor = ChuckerInterceptor.Builder(androidContext())
            .collector(ChuckerCollector(androidContext()))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(true)
            .build()

        val authInterceptor = AuthInterceptor()

        val okHttpClient= OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .callFactory(okHttpClient)
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
    single <FetchUserRepositoriesUseCase> { FetchUserRepositoriesUseCaseImpl(get()) }
}

private val viewModelModule: Module = module {
    viewModel { SearchViewModel(get(), get()) }
}

val dlightModules: List<Module> = listOf(
    databaseModule,
    networkModule,
    daoModule,
    repoModule,
    useCaseModule,
    viewModelModule
)
