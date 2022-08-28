package com.example.dlight.data.localSource.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["userName"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val url: String,
    val avatarUrl: String,
    val location: String,
    val userName: String,
    val name: String,
    val blog: String,
    val company: String,
    val bio: String,
    val followers: Int,
    val following: Int,
    val repositories: Int,
    val reposUrl: String,
    val followingUrl: String,
    val followersUrl: String,
    val organizationsUrl: String,
)