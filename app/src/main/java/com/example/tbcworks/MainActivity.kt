package com.example.tbcworks

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.enableEdgeToEdge
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpCounters()
        setUpButtons()
    }

    data class User(var firstName:String, var lastName:String, var age:Int, var email: String)

    private fun setUpButtons(){
        with(binding){


            btnAddUser.setOnClickListener {
                addUser()
            }
            btnDeleteUser.setOnClickListener {
                deleteUser()
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
    private fun updateUser(){
        with(binding){

                val userEmail = etEmail.text.toString().trim()
                val user = users.find { it.email == userEmail }

                val firstName = etFirstName.text.toString().trim()
                val lastName = etLastName.text.toString().trim()
                val age = etAge.text.toString().trim()

                var isUpdated = false

                if(user == null){
                    tvStatus.text = getString(R.string.user_doesnt_exists)
                    tvStatus.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.fail))
                    return
                }

                if(firstName.isNotEmpty() && firstName != user.firstName){
                    user.firstName = firstName
                    isUpdated = true
                }
                if(lastName.isNotEmpty() && lastName != user.lastName){
                    user.lastName = lastName
                    isUpdated = true
                }
                if(age.isNotEmpty() && age.toInt() != user.age){
                    user.age = age.toInt()
                    isUpdated = true
                }


                if(isUpdated){
                    tvStatus.text = getString(R.string.user_updated_successfully)
                    tvStatus.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.success))
                }else{
                    tvStatus.text = getString(R.string.fields_either_empty_or_same_value)
                    tvStatus.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.fail))
                }
        }
    }

    private fun deleteUser(){
        with(binding){
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val age = etAge.text.toString().trim()
            val email = etEmail.text.toString().trim()


            val userToDelete = users.find {
                it.firstName == firstName &&
                it.lastName == lastName &&
                        it.age == age.toInt() &&
                        it.email == email
            }

            if(userToDelete != null) {
                users.remove(userToDelete)
                deletedUsers++
                tvStatus.text = getString(R.string.user_deleted_successfully)
                tvStatus.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.success))
                tvActiveUsers.text = getString(R.string.active_users_with_placeholder, users.size)
                tvDeletedUsers.text = getString(R.string.deleted_users_with_placeholder, deletedUsers)
            }else{
                tvStatus.text = getString(R.string.user_doesnt_exists)
                tvStatus.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.fail))
            }
        }
    }

    private fun addUser(){
        with(binding){
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val age = etAge.text.toString().trim()
            val email = etEmail.text.toString().trim()


            var isValid = true

            if(firstName.isEmpty()){
                etFirstName.error = getString(R.string.firstname_cant_be_empty)
                isValid = false
            }
            if(lastName.isEmpty()){
                etLastName.error = getString(R.string.lastname_cant_be_empty)
                isValid = false
            }
            if(age.isEmpty()){
                etAge.error = getString(R.string.age_cant_be_empty)
                isValid = false
            }
            if(email.isEmpty()){
                etEmail.error = getString(R.string.email_cant_be_empty)
                isValid = false
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail.error = getString(R.string.invalid_email)
                isValid = false
            }

            val userExists = users.any {
                it.email == email
            }

            if(!isValid){
                return
            }
            if(!userExists){
                users.add(User(firstName,lastName,age.toInt(), email))
                tvActiveUsers.text = getString(R.string.active_users_with_placeholder, users.size)
                tvStatus.text = getString(R.string.user_added_successfully)
                tvStatus.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.success))
            }else{
                tvStatus.text = getString(R.string.user_already_exists)
                tvStatus.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.fail))
            }
        }

    }
}