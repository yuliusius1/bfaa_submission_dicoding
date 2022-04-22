package com.coba.githubuseryulius

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitUser(
    var usernameGitUser: String,
    var nameGitUser: String,
    var locationGitUser:String,
    var companyGitUser:String,
    var repositoryGitUser:String,
    var followersGitUser:String,
    var followingGitUser:String,
    var avatarGitUser: Int
): Parcelable
