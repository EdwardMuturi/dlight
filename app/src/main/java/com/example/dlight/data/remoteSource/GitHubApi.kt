package com.example.dlight.data.remoteSource

import com.example.dlight.data.localSource.GitHubUser
import com.example.dlight.data.localSource.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GitHubApi {
    @Headers("Accept: application/vnd.github.v3.full+json")
    @GET("/users/{username}")
    suspend fun getUser(@Path("username")  userName:String) : Response<GitHubUser>
}