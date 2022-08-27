package com.example.dlight.domain

import com.example.dlight.data.Result


interface FetchUserProfileUseCase {
    suspend operator fun invoke(userName: String) : Result
}