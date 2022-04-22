package com.coba.githubuseryulius.ui.detail


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coba.githubuseryulius.R
import com.coba.githubuseryulius.adapter.SectionAdapter
import com.coba.githubuseryulius.data.UserResponse
import com.coba.githubuseryulius.databinding.ActivityDetailUserBinding
import com.coba.githubuseryulius.ui.favorite.FavoriteActivity
import com.coba.githubuseryulius.ui.setting.SettingActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUser : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private var nameUser : String? = null
    private lateinit var avaUser : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val gitUserParcelable = intent.getParcelableExtra<UserResponse>(EXTRA_GITUSER) as UserResponse
        viewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]
        viewModel.setUserDetail(gitUserParcelable.login)
        viewModel.getUserDetail().observe(this,{
            if(it != null){
                nameUser = it.name
                avaUser = it.avatar_url
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvCompany.text = it.company
                    includeds.tvFollowers.text = it.followers.toString()
                    includeds.tvFollowing.text = it.following.toString()
                    includeds.tvRepository.text = it.public_repos.toString()
                    tvLocation.text = it.location
                    Glide.with(binding.root)
                        .load(it.avatar_url)
                        .apply(RequestOptions().override(250, 250))
                        .into(avaGithub)
                    title = it.login
                }
            }
        })

        var isFavorite = false
        CoroutineScope(Dispatchers.IO).launch {
            val countUser =  viewModel.checkData(gitUserParcelable.login)
            withContext(Dispatchers.Main){
                if(countUser > 0) {
                    binding.fabFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                    isFavorite = true
                } else {
                    binding.fabFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
        }

        binding.fabFav.setOnClickListener{
            isFavorite = !isFavorite
            if(isFavorite){
                viewModel.insertFavorite(gitUserParcelable.login, nameUser, avaUser)
                binding.fabFav.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                viewModel.deleteUserFavorite(gitUserParcelable.login)
                binding.fabFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        sectionPage()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.without_search,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }

            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun sectionPage() {
        val sectionAdapter = SectionAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_GITUSER = "extra_gituser"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onResume() {
        super.onResume()
        sectionPage()
    }
}