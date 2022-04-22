package com.coba.githubuseryulius.ui.setting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val THEME_KEY = intPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: 0
        }
    }

    suspend fun saveThemeSetting(tema: Int) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = tema
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}