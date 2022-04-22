package com.coba.githubuseryulius.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "gitUser_tbl")
@Parcelize
data class GithubUserFavorite (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "login")
    var login : String,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "avatar_url")
    val avatar_url: String
) : Parcelable