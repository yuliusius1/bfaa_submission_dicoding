package com.coba.githubuseryulius.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coba.githubuseryulius.api.ApiConfig
import com.coba.githubuseryulius.data.UserResponse
import com.coba.githubuseryulius.database.GithubUserFavorite
import com.coba.githubuseryulius.database.GithubUserFavoriteDao
import com.coba.githubuseryulius.database.GithubUserFavoriteRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<UserResponse>()

    private lateinit var githubUserFavoriteDao: GithubUserFavoriteDao
    private var githubUserFavoriteRoomDatabase : GithubUserFavoriteRoomDatabase =
        GithubUserFavoriteRoomDatabase.getDatabase(application)

    init {
        githubUserFavoriteDao = githubUserFavoriteRoomDatabase.githubUserFavoriteDao()
    }

    fun setUserDetail(username: String){
        ApiConfig.getApiService().getDetailUser(username)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if(response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {

                }
            })
    }

    fun getUserDetail() : LiveData<UserResponse>{
        return user
    }

    fun insertFavorite(login: String, name: String?, avatar_url:String){
        CoroutineScope(Dispatchers.IO).launch {
            val gitUser = GithubUserFavorite(
                0 ,login, name, avatar_url
            )
            githubUserFavoriteDao.insert(gitUser)
        }
    }
    suspend fun checkData(query: String) = githubUserFavoriteDao.checkUser(query)
    fun deleteUserFavorite(query : String){
        CoroutineScope(Dispatchers.IO).launch{
            githubUserFavoriteDao.deleteUser(query)
        }
    }
}