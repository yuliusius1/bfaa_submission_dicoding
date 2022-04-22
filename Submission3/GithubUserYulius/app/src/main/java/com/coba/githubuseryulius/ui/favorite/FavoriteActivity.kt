package com.coba.githubuseryulius.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.coba.githubuseryulius.R
import com.coba.githubuseryulius.adapter.FavoriteAdapter
import com.coba.githubuseryulius.adapter.ListGitUserAdapter
import com.coba.githubuseryulius.data.UserResponse
import com.coba.githubuseryulius.databinding.ActivityFavoriteBinding
import com.coba.githubuseryulius.ui.detail.DetailUser
import com.coba.githubuseryulius.ui.setting.SettingActivity
import com.coba.githubuseryulius.ui.setting.VMFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter : FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteAdapter()
        title = "Favorite Github User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter.setOnItemClickCallback(object: ListGitUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserResponse) {
                showSelectedUser(data)
            }
        })
        data()

    }

    private fun obtainViewModel(activity: FavoriteActivity): FavoriteViewModel {
        val factory = VMFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun data() {
        showLoading(true)
        binding.apply {
            rvListGitUserFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvListGitUserFavorite.adapter = adapter
            rvListGitUserFavorite.setHasFixedSize(true)
        }

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllGithubUserFavorite().observe(this, { noteList ->
            if (noteList != null) {
                adapter.setData(noteList)
            }
            showLoading(false)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.only_setting,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showSelectedUser(data: UserResponse) {
        val moveWithObjectIntent = Intent(this@FavoriteActivity, DetailUser::class.java)
        moveWithObjectIntent.putExtra(DetailUser.EXTRA_GITUSER, data)
        startActivity(moveWithObjectIntent)
    }

    override fun onResume() {
        super.onResume()
        data()
    }

    private fun showLoading(b: Boolean) {
        if (b) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
    }

}