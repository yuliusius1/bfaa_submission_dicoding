package com.coba.githubuseryulius

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.coba.githubuseryulius.adapter.ListGitUserAdapter
import com.coba.githubuseryulius.api.ApiConfig
import com.coba.githubuseryulius.data.GithubResponse
import com.coba.githubuseryulius.data.UserResponse
import com.coba.githubuseryulius.databinding.ActivityMainBinding
import com.coba.githubuseryulius.detail.DetailUser
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private var listUser = ArrayList<UserResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        mainBinding.rvListGitUser.setHasFixedSize(true)
        getUserDefault()
        showRVList()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenu = menu.findItem(R.id.search).actionView as SearchView

        searchMenu.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchMenu.queryHint = resources.getString(R.string.search_hint)
        searchMenu.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMenu.clearFocus()
                searchUser(query)
                return true
            }
            override fun onQueryTextChange(str: String): Boolean {
                if(str == "") getUserDefault()
                return false
            }
        })
        return true
    }

    private fun getUserDefault() {
        showLoading(true)
        val client = ApiConfig.getApiService().getDefaultUser()
        client.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUsersData(responseBody)
                    }
                } else {
                    Toast.makeText(this@MainActivity,"Data Not Found", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@MainActivity,"Data Failure", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setUsersData(data: List<UserResponse>) {
        for(i in data.indices) {
            getUserDetails(data[i].login)
        }
    }

    private fun searchUser(query: String){
        showLoading(true)
        val client = ApiConfig.getApiService().getSearchUser(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody!!.totalCount >= 1) {
                        listUser.clear()
                        setUserData(responseBody.items)
                    } else {
                        Toast.makeText(this@MainActivity,"User $query Not Found", Toast.LENGTH_SHORT).show()
                        listUser.clear()
                        showRVList()
                    }
                } else {
                    Toast.makeText(this@MainActivity,"Error", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setUserData(datas: List<UserResponse>) {
        for(data in datas) {
            getUserDetails(data.login)
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
                    if (responseBody != null) {
                        listUser.add(responseBody)
                        showRVList()
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

    private fun showRVList(){
        mainBinding.rvListGitUser.layoutManager = LinearLayoutManager(this)
        val listGitUserAdapter = ListGitUserAdapter(listUser)
        mainBinding.rvListGitUser.adapter = listGitUserAdapter

        listGitUserAdapter.setOnItemClickCallback(object : ListGitUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserResponse) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(data: UserResponse) {
        val moveWithObjectIntent = Intent(this@MainActivity, DetailUser::class.java)
        moveWithObjectIntent.putExtra(DetailUser.EXTRA_GITUSER, data)
        startActivity(moveWithObjectIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        mainBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}