package com.coba.githubuseryulius

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView




class DetailUser : AppCompatActivity() {

    companion object {
        const val EXTRA_GITUSER = "extra_gituser"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        val tvName:TextView = findViewById(R.id.tvName)
        val tvUsername:TextView = findViewById(R.id.tvUsername)
        val tvLocation:TextView = findViewById(R.id.tvLocation)
        val tvCompany:TextView = findViewById(R.id.tvCompany)
        val tvFollowing:TextView = findViewById(R.id.tv_following)
        val tvFollowers:TextView = findViewById(R.id.tv_followers)
        val tvRepository:TextView = findViewById(R.id.tv_repository)
        val avaGithub:ImageView = findViewById(R.id.avaGithub)
        val gitUserParcelable = intent.getParcelableExtra<GitUser>(EXTRA_GITUSER) as GitUser
//        val text = "Name : ${gitUserParcelable.nameGitUser}, \nUsername : ${gitUserParcelable.usernameGitUser}, \nAvatar : ${gitUserParcelable.avatarGitUser}"
        tvName.text = gitUserParcelable.nameGitUser
        tvUsername.text = gitUserParcelable.usernameGitUser
        tvFollowers.text = gitUserParcelable.followersGitUser
        tvFollowing.text = gitUserParcelable.followingGitUser
        tvRepository.text = gitUserParcelable.repositoryGitUser
        tvLocation.text = gitUserParcelable.locationGitUser
        tvCompany.text = gitUserParcelable.companyGitUser
        avaGithub.setImageResource(gitUserParcelable.avatarGitUser)
        setTitle(gitUserParcelable.usernameGitUser)
    }
}