package com.example.tbcworks

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcworks.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var users: MutableList<User> = mutableListOf()
    private var deletedUsers = 0

    private val userReciever = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if (result.resultCode == RESULT_OK){
            result.data?.let { data ->
                val user = data.getSerializableExtra("user") as? User
                val operation = data.getStringExtra("operation")

                user?.let {
                    when (operation) {
                        "add" ->
                            users.add(it)

                        "update" -> {
                            val index = users.indexOfFirst { u -> u.email == it.email }
                            if (index != -1) users[index] = it
                        }
                        "delete" -> {
                            users.remove(user)
                            deletedUsers++
                        }
                    }
                    setUpCounters()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUp()
    }
    private fun setUp(){
        setUpCounters()
        setUpButtons()
    }

    private fun setUpButtons(){
        with(binding){


            btnAddUser.setOnClickListener {
                addUser()
            }
            btnUpdateUser.setOnClickListener {
                updateUser()
            }
        }

    }

    private fun setUpCounters(){
        with(binding){
            tvActiveUsers.text = getString(R.string.active_users_with_placeholder, users.size)
            tvDeletedUsers.text = getString(R.string.deleted_users_with_placeholder, deletedUsers)
        }

    }
    private fun updateUser() = with(binding) {
        if(users.isEmpty()){
            SnackbarHelper.show(root,getString(R.string.no_active_users))
            return@with
        }
        val user = users.random()
        val intent = Intent(this@MainActivity, UserManagmentActivity::class.java)
        intent.apply {
            putExtra("user", user)
            putExtra("operation", "update")
        }
        userReciever.launch(intent)
    }

    private fun addUser(){
        with(binding){
           val intent = Intent(this@MainActivity, UserManagmentActivity::class.java)
            intent.putExtra("operation", "add")
            userReciever.launch(intent)
        }

    }
}