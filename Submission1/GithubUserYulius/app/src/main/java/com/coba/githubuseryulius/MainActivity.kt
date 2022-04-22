package com.coba.githubuseryulius

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rv_listGitUser: RecyclerView
    private var listData = ArrayList<GitUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_listGitUser = findViewById(R.id.rv_listGitUser)
        rv_listGitUser.setHasFixedSize(true)

        listData.addAll(listGithubUser)
        showRVList()
    }

    private val listGithubUser : ArrayList<GitUser>
    get() {
        val dataUsername = resources.getStringArray(R.array.username)
        val dataName = resources.getStringArray(R.array.name)
        val dataLocation = resources.getStringArray(R.array.location)
        val dataCompany = resources.getStringArray(R.array.company)
        val dataRepository = resources.getStringArray(R.array.repository)
        val dataFollowers = resources.getStringArray(R.array.followers)
        val dataFollowing = resources.getStringArray(R.array.following)
        val dataAvatar = resources.obtainTypedArray(R.array.avatar)
        val listGitUsers = ArrayList<GitUser>()

        for(i in dataUsername.indices){
            val GitUsers = GitUser(
                dataUsername[i],
                dataName[i],
                dataLocation[i],
                dataCompany[i],
                dataRepository[i],
                dataFollowers[i],
                dataFollowing[i],
                dataAvatar.getResourceId(i,-1)
            )
            listGitUsers.add(GitUsers)
        }
        return listGitUsers
    }

    private fun showRVList(){
        rv_listGitUser.layoutManager = LinearLayoutManager(this)
        val listGitUserAdapter = ListGitUserAdapter(listData)
        rv_listGitUser.adapter = listGitUserAdapter

        listGitUserAdapter.setOnItemClickCallback(object : ListGitUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GitUser) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(data: GitUser) {

        val GitUsers = GitUser(
            data.usernameGitUser,
            data.nameGitUser,
            data.locationGitUser,
            data.companyGitUser,
            data.repositoryGitUser,
            data.followersGitUser,
            data.followingGitUser,
            data.avatarGitUser
        )
        val moveWithObjectIntent = Intent(this@MainActivity, DetailUser::class.java)
        moveWithObjectIntent.putExtra(DetailUser.EXTRA_GITUSER, GitUsers)
        startActivity(moveWithObjectIntent)
    }

}