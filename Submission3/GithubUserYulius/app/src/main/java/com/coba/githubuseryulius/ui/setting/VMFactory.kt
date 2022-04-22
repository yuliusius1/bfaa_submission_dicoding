package com.coba.githubuseryulius.ui.setting

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coba.githubuseryulius.ui.detail.DetailUserViewModel
import com.coba.githubuseryulius.ui.favorite.FavoriteViewModel

class VMFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory(){

    companion object {
        @Volatile
        private var INSTANCE: VMFactory? = null
        @JvmStatic
        fun getInstance(application: Application): VMFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = VMFactory(application)
                }
            }
            return INSTANCE as VMFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}