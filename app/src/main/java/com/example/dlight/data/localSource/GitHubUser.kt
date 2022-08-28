package com.example.dlight.data.localSource


import com.example.dlight.data.localSource.model.User
import com.google.gson.annotations.SerializedName

data class GitHubUser(
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
    @SerializedName("bio")
    val bio: String = "",
    @SerializedName("blog")
    val blog: String = "",
    @SerializedName("company")
    val company: String?= null,
    @SerializedName("created_at")
    val createdAt: String?= null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("events_url")
    val eventsUrl: String?= null,
    @SerializedName("followers")
    val followers: Int = 0,
    @SerializedName("followers_url")
    val followersUrl: String?= null,
    @SerializedName("following")
    val following: Int = 0,
    @SerializedName("following_url")
    val followingUrl: String?= null,
    @SerializedName("gists_url")
    val gistsUrl: String?= null,
    @SerializedName("gravatar_id")
    val gravatarId: String?= null,
    @SerializedName("hireable")
    val hireable: Boolean = false,
    @SerializedName("html_url")
    val htmlUrl: String?= null,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("node_id")
    val nodeId: String?= null,
    @SerializedName("organizations_url")
    val organizationsUrl: String?= null,
    @SerializedName("public_gists")
    val publicGists: Int = 0,
    @SerializedName("public_repos")
    val publicRepos: Int,
    @SerializedName("received_events_url")
    val receivedEventsUrl: String?= null,
    @SerializedName("repos_url")
    val reposUrl: String?= null,
    @SerializedName("site_admin")
    val siteAdmin: Boolean = false,
    @SerializedName("starred_url")
    val starredUrl: String?= null,
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String?= null,
    @SerializedName("twitter_username")
    val twitterUsername: String?= null,
    @SerializedName("type")
    val type: String?= null,
    @SerializedName("updated_at")
    val updatedAt: String?= null,
    @SerializedName("url")
    val url: String?= null
)

fun GitHubUser.asUser()= User(
    0,
    this.url ?: "",
    this.avatarUrl,
    this.location?: "",
    this.login?: "",
    this.name?: "",
    this.blog,
    this.company?: "",
    this.bio,
    this.followers,
    this.following,
    this.publicRepos,
    this.reposUrl?: "",
    this.followingUrl?: "",
    this.followersUrl?: "",
    this.organizationsUrl?: "",
)