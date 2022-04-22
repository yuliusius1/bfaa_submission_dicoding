package com.coba.githubuseryulius.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.coba.githubuseryulius.database.GithubUserFavorite
import com.coba.githubuseryulius.database.GithubUserFavoriteDao
import com.coba.githubuseryulius.database.GithubUserFavoriteRoomDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var githubUserFavoriteDao: GithubUserFavoriteDao
    private lateinit var githubUserFavoriteRoomDatabase : GithubUserFavoriteRoomDatabase


    init {
        githubUserFavoriteRoomDatabase=  GithubUserFavoriteRoomDatabase.getDatabase(application)
        githubUserFavoriteDao = githubUserFavoriteRoomDatabase.githubUserFavoriteDao()
    }

    fun getAllGithubUserFavorite() : LiveData<List<GithubUserFavorite>> {
        return githubUserFavoriteDao.getAllGithubUserFavorite()
    }


}