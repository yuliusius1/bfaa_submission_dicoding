package com.coba.githubuseryulius.api

import com.coba.githubuseryulius.data.GithubResponse
import com.coba.githubuseryulius.data.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users/{id}")
    fun getDetailUser(
        @Path("id")
        id: String
    ): Call<UserResponse>

    @GET("users")
    fun getDefaultUser(
    ): Call<List<UserResponse>>

    @GET("search/users")
    fun getSearchUser(
        @Query("q")
        query: String
    ): Call<GithubResponse>

    @GET("users/{id}/followers")
    fun getFollowers(
        @Path("id")
        id: String
    ): Call<ArrayList<UserResponse>>

    @GET("users/{id}/following")
    fun getFollowing(
        @Path("id")
        id: String
    ): Call<ArrayList<UserResponse>>
}