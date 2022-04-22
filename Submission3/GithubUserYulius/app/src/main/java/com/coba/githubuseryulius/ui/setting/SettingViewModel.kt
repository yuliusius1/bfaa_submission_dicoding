package com.coba.githubuseryulius.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPreferences): ViewModel() {
    fun getThemeSetting(): LiveData<Int> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(tema : Int){
        viewModelScope.launch {
            pref.saveThemeSetting(tema)
        }
    }
}