package com.coba.githubuseryulius.helper

import androidx.recyclerview.widget.DiffUtil
import com.coba.githubuseryulius.database.GithubUserFavorite

class UserDiffCallback(private val mOldUser: List<GithubUserFavorite>, private val mNewUser: List<GithubUserFavorite>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldUser.size
    }

    override fun getNewListSize(): Int {
        return mNewUser.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUser[oldItemPosition].id == mNewUser[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = mOldUser[oldItemPosition]
        val newUser = mNewUser[newItemPosition]
        return oldUser.name == newUser.name &&
                oldUser.login == newUser.login &&
                oldUser.avatar_url == newUser.avatar_url
    }


}