package com.example.dlight.domain

import com.example.dlight.data.localSource.model.Followers
import com.example.dlight.data.localSource.model.Followings

interface FetchUserFollowingUseCase {
    suspend operator fun invoke(userName: String) : List<Followings>
}