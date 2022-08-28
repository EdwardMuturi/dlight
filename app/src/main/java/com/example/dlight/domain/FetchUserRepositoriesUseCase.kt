package com.example.dlight.domain

import com.example.dlight.data.localSource.model.Repository
import kotlinx.coroutines.flow.Flow

interface FetchUserRepositoriesUseCase {
    suspend operator fun invoke(userName: String) : List<Repository>
}