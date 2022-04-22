package com.coba.githubuseryulius

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListGitUserAdapter(private val listGitUsers: ArrayList<GitUser>): RecyclerView.Adapter<ListGitUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface  OnItemClickCallback{
        fun onItemClicked(data: GitUser)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(v:View): RecyclerView.ViewHolder(v){
        var avaGithub : ImageView = v.findViewById(R.id.avaGithub)
        var tv_GitName : TextView = v.findViewById(R.id.tv_GitName)
        var tv_GitUserName : TextView = v.findViewById(R.id.tv_GitUserName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.cv_githubuser_items, parent, false)
        return ListViewHolder(v)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(name,username, location,company,repository, followers, following, avatar) = listGitUsers[position]

        holder.avaGithub.setImageResource(avatar)
        holder.tv_GitName.text = name
        holder.tv_GitUserName.text = username
        holder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked((listGitUsers[holder.adapterPosition]))}
    }

    override fun getItemCount(): Int = listGitUsers.size
}