package com.coba.githubuseryulius.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GithubUserFavorite::class], exportSchema = false, version = 1)
abstract class GithubUserFavoriteRoomDatabase : RoomDatabase() {
    abstract fun githubUserFavoriteDao() : GithubUserFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: GithubUserFavoriteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GithubUserFavoriteRoomDatabase{
            if (INSTANCE == null) {
                synchronized(GithubUserFavoriteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        GithubUserFavoriteRoomDatabase::class.java, "github_user_favorite_database")
                        .build()
                }
            }
            return INSTANCE as GithubUserFavoriteRoomDatabase
        }
    }
}