package com.coba.githubuseryulius.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coba.githubuseryulius.data.UserResponse
import com.coba.githubuseryulius.database.GithubUserFavorite
import com.coba.githubuseryulius.databinding.CvGithubuserItemsBinding
import com.coba.githubuseryulius.helper.UserDiffCallback

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {
    private val listNotes = ArrayList<GithubUserFavorite>()
    private lateinit var onItemClickCallback: ListGitUserAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: ListGitUserAdapter.OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(listNotes: List<GithubUserFavorite>) {
        val diffCallback = UserDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = CvGithubuserItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    inner class FavViewHolder (private val binding: CvGithubuserItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUserFavorite) {
            with(binding) {
                tvGitUserName.text = user.login
                tvGitName.text = user.name
                Glide.with(root)
                    .load(user.avatar_url)
                    .apply(RequestOptions().override(250, 250))
                    .into(avaGithub)
                root.setOnClickListener {
                    val listData = mapList(listNotes[adapterPosition])
                    onItemClickCallback.onItemClicked(listData)
                }
            }
        }
    }

    private fun mapList(lists: GithubUserFavorite): UserResponse {
        val gitUser = UserResponse(
            lists.login,
            lists.name,
            null,
            null,
            null,
            null,
            null,
            lists.avatar_url
        )

        return gitUser
    }
}