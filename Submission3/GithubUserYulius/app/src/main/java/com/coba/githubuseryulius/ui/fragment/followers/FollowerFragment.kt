package com.coba.githubuseryulius.ui.fragment.followers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.coba.githubuseryulius.adapter.FollAdapter
import com.coba.githubuseryulius.adapter.ListGitUserAdapter
import com.coba.githubuseryulius.data.UserResponse
import com.coba.githubuseryulius.databinding.FragmentFollowerBinding
import com.coba.githubuseryulius.ui.detail.DetailUser

class FollowerFragment : Fragment() {
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var adapter: FollAdapter
    private lateinit var viewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gitUserParcelable = requireActivity().intent.getParcelableExtra<UserResponse>(DetailUser.EXTRA_GITUSER) as UserResponse

        adapter = FollAdapter()
        showLoading(true)

        adapter.setOnItemClickCallback(object : ListGitUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserResponse) {
                showSelectedUser(data)
            }
        })

        binding.apply {
            rvListFollower.layoutManager = LinearLayoutManager(activity)
            rvListFollower.setHasFixedSize(true)
            rvListFollower.adapter = adapter
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowersViewModel::class.java]
        viewModel.setListFolls(gitUserParcelable.login)
        viewModel.getListFolls().observe(viewLifecycleOwner, {
            if(it != null){
                adapter.setData(it)
                showLoading(false)
            }
        })
    }

    private fun showSelectedUser(data: UserResponse) {
        val moveWithObjectIntent = Intent(activity, DetailUser::class.java)
        moveWithObjectIntent.putExtra(DetailUser.EXTRA_GITUSER, data)
        startActivity(moveWithObjectIntent)
    }

    private fun showLoading(b: Boolean) {
        if (b) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
    }
}