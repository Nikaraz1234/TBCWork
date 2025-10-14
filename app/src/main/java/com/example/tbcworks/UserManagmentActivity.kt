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
        checkUpdateOrAdd()
    }
    private fun setUpListeners() = with(binding){
        btnAddUser.setOnClickListener{
            addUser()
        }
        btnUpdateUser.setOnClickListener {
            updateUser()
        }
        btnDeleteUser.setOnClickListener {
            deleteUser()
        }
    }
    private fun checkUpdateOrAdd() = with(binding){
        val operationMode = intent.getStringExtra("operation")
        val currentUser = intent.getSerializableExtra("user") as? User

        if(operationMode == "update"){
            btnAddUser.visibility = View.GONE
            btnUpdateUser.visibility = View.VISIBLE
            btnDeleteUser.visibility = View.VISIBLE

            if(currentUser != null){
                etFirstName.setText(currentUser.firstName)
                etLastName.setText(currentUser.lastName)
                etAge.setText(currentUser.age.toString())
                etEmail.setText(currentUser.email)
                etEmail.isEnabled = false
            }
        }
    }

    private fun deleteUser() = with(binding){
        val user = intent.getSerializableExtra("user") as? User
        val intent = Intent().apply {
            putExtra("user", user)
            putExtra("operation", "delete")
        }
        setResult(RESULT_OK, intent)
        finish()

    }

    private fun updateUser() = with(binding){
        val email = etEmail.text.toString().trim()
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val age = etAge.text.toString().trim()

        if(validateInputs(firstName,lastName,age,email)){
            val user = User(firstName, lastName, age.toInt(), email)
            val intent = Intent().apply {
                putExtra("user", user)
                putExtra("operation", "update")
            }
            setResult(RESULT_OK, intent)
            finish()
        }

    }

    private fun addUser()= with(binding){
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val age = etAge.text.toString().trim()
        val email = etEmail.text.toString().trim()

        if(validateInputs(firstName,lastName,age,email)){
            val user = User(firstName, lastName, age.toInt(), email)

            val intent = Intent().apply {
                putExtra("user", user)
                putExtra("operation", "add")
            }
            setResult(RESULT_OK,intent)
            finish()
        }


    }

    private fun validateInputs(firstName : String, lastName:String, age:String, email:String) : Boolean = with(binding){
        if(firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || email.isEmpty()){
            SnackbarHelper.show(root, "Fill all info")
            return@with false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.error = "Invalid email"
            return@with false
        }
        return@with true
    }


}