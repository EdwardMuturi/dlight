package com.example.dlight.data.localSource.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["login"], unique = true)])
data class Followings(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val userLogin: String,
    val login: String,
    val name: String,
    val avatarUrl: String,
    val bioHtml: String,
)