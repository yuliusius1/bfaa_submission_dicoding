package com.coba.githubuseryulius.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GithubUserFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(githubUserFavorite : GithubUserFavorite)

    @Query("select count(*) from gitUser_tbl where login = :query")
    suspend fun checkUser(query: String): Int

    @Query("SELECT * from gitUser_tbl order by id asc")
    fun getAllGithubUserFavorite() : LiveData<List<GithubUserFavorite>>

    @Query("delete from gitUser_tbl where login = :query")
    suspend fun deleteUser(query: String) : Int
}