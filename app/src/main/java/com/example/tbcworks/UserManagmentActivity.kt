package com.example.tbcworks

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcworks.databinding.ActivityMainBinding
import com.example.tbcworks.databinding.ActivityUserManagmentBinding

class UserManagmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserManagmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserManagmentBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener (binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUp()
    }

    private fun setUp(){
        setUpListeners()
    }
    private fun setUpListeners() = with(binding){
        btnAddUser.setOnClickListener{
            addUser()
        }
    }




    private fun addUser()= with(binding){
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val birthday = etBirthday.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val email = etEmail.text.toString().trim()

        val lastUserId = intent.getIntExtra("userId", 0)
        val index = lastUserId + 1

        if(validateInputs(firstName,lastName,birthday,address,email)){
            val user = User(index,firstName,lastName,birthday, address, email)
            val intent = Intent().putExtra("user", user)
            setResult(RESULT_OK, intent)
            finish()
        }else{
            SnackbarHelper.show(binding.root, getString(R.string.cant_create_user))
        }

    }

    private fun validateInputs(firstName : String, lastName:String,birthday:String, address:String, email:String) : Boolean = with(binding){
        if(firstName.isEmpty() || lastName.isEmpty() || birthday.isEmpty() || address.isEmpty() || email.isEmpty()){
            SnackbarHelper.show(root, getString(R.string.fill_all_info))
            return@with false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.error = getString(R.string.invalid_email)
            return@with false
        }
        return@with true
    }


}