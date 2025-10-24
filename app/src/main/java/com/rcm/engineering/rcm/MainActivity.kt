package com.rcm.engineering.rcm

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rcm.engineering.rcm.adapter.UserAdapter
import com.rcm.engineering.rcm.api.RetrofitClient
import com.rcm.engineering.rcm.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val users = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UserAdapter(users)

        fetchUsers()
    }

    private fun fetchUsers() {
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                RetrofitClient.apiService.fetchAllUsers()
            }.onSuccess { response ->
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        users.clear()
                        response.body()?.let { users.addAll(it) }
                        recyclerView.adapter?.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@MainActivity, "Failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            }.onFailure { e ->
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}