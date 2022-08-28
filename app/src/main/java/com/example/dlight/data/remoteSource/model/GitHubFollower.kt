package com.example.dlight.data.remoteSource.model


import com.example.dlight.data.localSource.model.Followers
import com.example.dlight.data.localSource.model.Followings
import com.google.gson.annotations.SerializedName

data class GitHubFollower(
    @SerializedName("avatar_url")
    val avatarUrl: String ?= null,
    @SerializedName("events_url")
    val eventsUrl: String ?= null,
    @SerializedName("followers_url")
    val followersUrl: String ?= null,
    @SerializedName("following_url")
    val followingUrl: String ?= null,
    @SerializedName("gists_url")
    val gistsUrl: String ?= null,
    @SerializedName("gravatar_id")
    val gravatarId: String ?= null,
    @SerializedName("html_url")
    val htmlUrl: String ?= null,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("login")
    val login: String ?= null,
    @SerializedName("node_id")
    val nodeId: String ?= null,
    @SerializedName("organizations_url")
    val organizationsUrl: String ?= null,
    @SerializedName("received_events_url")
    val receivedEventsUrl: String ?= null,
    @SerializedName("repos_url")
    val reposUrl: String ?= null,
    @SerializedName("site_admin")
    val siteAdmin: Boolean = false,
    @SerializedName("starred_url")
    val starredUrl: String ?= null,
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String ?= null,
    @SerializedName("type")
    val type: String ?= null,
    @SerializedName("url")
    val url: String ?= null
)

fun GitHubFollower.asFollower() = Followers(
    0,
this.login ?: "",
this.login ?: "",
this.avatarUrl ?: "",
this.url ?: "",
)

fun GitHubFollower.asFollowings() = Followings(
    0,
this.login ?: "",
this.login ?: "",
this.avatarUrl ?: "",
this.url ?: "",
)