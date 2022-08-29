package com.example.dlight.domain

import com.example.dlight.data.localSource.model.Followers

interface FetchUserFollowersUseCase {
    suspend operator fun invoke(userName: String) : List<Followers>
}