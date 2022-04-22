package com.coba.githubuseryulius.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coba.githubuseryulius.R
import com.coba.githubuseryulius.data.UserResponse

class ListGitUserAdapter(private val listGitUsers: ArrayList<UserResponse>): RecyclerView.Adapter<ListGitUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    interface  OnItemClickCallback{
        fun onItemClicked(data: UserResponse)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(v:View): RecyclerView.ViewHolder(v){
        var avaGithub : ImageView = v.findViewById(R.id.avaGithub)
        var tvGitName : TextView = v.findViewById(R.id.tv_GitName)
        var tvGitUserName : TextView = v.findViewById(R.id.tv_GitUserName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.cv_githubuser_items, parent, false)
        return ListViewHolder(v)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(login,username, _,_,_,_,_, avatar) = listGitUsers[position]

        Glide.with(holder.itemView.context)
            .load(avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.avaGithub)
        holder.tvGitName.text = login
        holder.tvGitUserName.text = username
        holder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked((listGitUsers[holder.adapterPosition]))}
    }

    override fun getItemCount(): Int = listGitUsers.size
}