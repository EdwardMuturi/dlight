package com.example.dlight.domain

sealed class Result{
    data class Success(val result: Any) : Result()
    data class Error(val message: String) : Result()
}
