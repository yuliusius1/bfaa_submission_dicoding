package com.coba.githubuseryulius.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
	val login: String,
	val name: String? = null,
	val location: String? = null,
	val company: String? = null,
	val public_repos: Int,
	val followers: Int,
	val following: Int,
	val avatar_url: String,
) : Parcelable
