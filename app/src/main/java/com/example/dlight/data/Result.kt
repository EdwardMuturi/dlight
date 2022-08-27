package com.example.dlight.data

sealed class Result{
    data class Success<T>(val result: T) : Result()
    data class Error(val message: String) : Result()
}
