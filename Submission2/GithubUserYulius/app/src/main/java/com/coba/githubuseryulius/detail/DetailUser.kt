package com.coba.githubuseryulius.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coba.githubuseryulius.R
import com.coba.githubuseryulius.adapter.SectionAdapter
import com.coba.githubuseryulius.data.UserResponse
import com.coba.githubuseryulius.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val gitUserParcelable = intent.getParcelableExtra<UserResponse>(EXTRA_GITUSER) as UserResponse
        getData(gitUserParcelable)
        sectionPage()
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

    private fun getData(gitUserParcelable: UserResponse) {
        binding.tvName.text = gitUserParcelable.name
        binding.tvUsername.text = gitUserParcelable.login
        binding.includeds.tvFollowers.text = gitUserParcelable.followers.toString()
        binding.includeds.tvFollowing.text = gitUserParcelable.following.toString()
        binding.includeds.tvRepository.text = gitUserParcelable.public_repos.toString()
        binding.tvLocation.text = gitUserParcelable.location
        binding.tvCompany.text = gitUserParcelable.company
        Glide.with(binding.root)
            .load(gitUserParcelable.avatar_url)
            .apply(RequestOptions().override(250, 250))
            .into(binding.avaGithub)
        title = gitUserParcelable.login
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