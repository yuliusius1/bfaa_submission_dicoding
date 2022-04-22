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

class FollAdapter : RecyclerView.Adapter<FollAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: ListGitUserAdapter.OnItemClickCallback
    private val listData = ArrayList<UserResponse>()

    fun setData(data: ArrayList<UserResponse>){
        listData.clear()
        listData.addAll(data)
    }

    fun setOnItemClickCallback(onItemClickCallback: ListGitUserAdapter.OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(v:View): RecyclerView.ViewHolder(v){
        var avaGithub : ImageView = v.findViewById(R.id.avaGithub)
        var tvGitName : TextView = v.findViewById(R.id.tv_GitName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.cv_gituser_f_items, parent, false)
        return ListViewHolder(v)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(login,_, _,_,_,_,_, avatar) = listData[position]

        Glide.with(holder.itemView.context)
            .load(avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.avaGithub)
        holder.tvGitName.text = login
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listData[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listData.size

}