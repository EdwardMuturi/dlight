package com.example.dlight.data.localSource.model


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(indices = [Index(value = ["login"], unique = true)])
data class Repository(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String,
    val login: String,
    val forksCount: Int ,
    val fullName: String,
    val htmlUrl: String,
    val nodeId: String,
    val stargazersCount: Int,
    val watchersCount: Int
)