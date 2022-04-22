package com.coba.githubuseryulius.ui.fragment.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coba.githubuseryulius.api.ApiConfig
import com.coba.githubuseryulius.data.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    val listFolls = MutableLiveData<ArrayList<UserResponse>>()

    fun setListFollowing(username:String){
        ApiConfig.getApiService().getFollowing(username).enqueue(object:
            Callback<ArrayList<UserResponse>> {
            override fun onResponse(
                call: Call<ArrayList<UserResponse>>,
                response: Response<ArrayList<UserResponse>>
            ) {
                if(response.isSuccessful){
                    listFolls.postValue(response.body())
                } else {
                    Log.d("Followers View model", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<UserResponse>>, t: Throwable) {
                Log.d("Failure", "onFailure: ${t.message}")
            }

        })
    }

    fun getListFollowing() : LiveData<ArrayList<UserResponse>> {
        return listFolls
    }
}