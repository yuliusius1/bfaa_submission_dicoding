package com.coba.githubuseryulius.fragment.following

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.coba.githubuseryulius.adapter.ListGitUserAdapter
import com.coba.githubuseryulius.api.ApiConfig
import com.coba.githubuseryulius.data.UserResponse
import com.coba.githubuseryulius.databinding.FragmentFollowingBinding
import com.coba.githubuseryulius.detail.DetailUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private var listUser = ArrayList<UserResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gitUserParcelable = requireActivity().intent.getParcelableExtra<UserResponse>(DetailUser.EXTRA_GITUSER) as UserResponse
        binding.rvListFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvListFollowing.adapter = ListGitUserAdapter(listUser)
        getFollowingsUser(gitUserParcelable.login)
        showRsVList()
    }

    private fun getFollowingsUser(usernameGitUser: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowing(usernameGitUser)
        client.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                showLoading(false)
                Log.d(TAG, response.body().toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        listUser.clear()
                        setData(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setData(datas: List<UserResponse>) {
        for(i in datas.indices) {
            getUserDetails(datas[i].login)
        }
    }

    private fun getUserDetails(query: String){
        showLoading(true)
        val client = ApiConfig.getApiService().getDetailUser(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d(TAG, response.body().toString())
                    if (responseBody != null) {
                        listUser.add(responseBody)
                        showRsVList()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }

    private fun showRsVList(){
        val listFollowingAdapter = ListGitUserAdapter(listUser)
        binding.rvListFollowing.adapter = listFollowingAdapter

        listFollowingAdapter.setOnItemClickCallback(object : ListGitUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserResponse) {
                showSelectedUser(data)
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


    companion object {
        private val TAG = FollowingFragment::class.java.simpleName
    }
}