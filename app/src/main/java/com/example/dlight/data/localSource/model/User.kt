package com.example.dlight.data.localSource.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(indices = [Index(value = ["userName"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val url: String,
    val avatarUrl: String,
    val bioHtml: String,
    val companyHtml: String,
    val email: String,
    val followersTotalCount: Int,
    val followingTotalCount: Int,
    val isDeveloperProgramMember: Boolean,
    val isVerified: Boolean,
    val isEmployee: Boolean,
    val isViewer: Boolean,
    val location: String,
    @SerializedName("login")
    val userName: String?,
    val name: String,
    val organizationsCount: Int,
    val repositoriesCount: Int,
    val starredRepositoriesCount: Int,
    val viewerCanFollow: Boolean,
    val viewerIsFollowing: Boolean,
    val websiteUrl: String,
    val isOrganization: Boolean,
    val emojiHtml: String,
    val indicatesLimitedAvailability: Boolean,
    val message: String
)