package com.example.tbcworks

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcworks.databinding.ActivityMainBinding
import android.view.View

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var userList = mutableListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addUserSetUp()
        getUserSetUp()
        userCounterSetUp()
    }
    data class User(val email: String, val fullName: String)

    fun addUserSetUp(){
        val btnAddUser = binding.btnAddUser

        btnAddUser.setOnClickListener {
            val email = binding.emailReg.text.toString().trim()
            val fullName = binding.fullNameAdd.text.toString().trim()

            var isValid = true
            if(email.isEmpty()){
                binding.emailReg.error = "Email is required"
                isValid = false

            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailReg.error = "Enter valid email"
                isValid = false
            }

            if(fullName.isEmpty()){
                binding.fullNameAdd.error = "Enter full name"
                isValid = false
            }else if(!fullName.contains(" ")){
                binding.fullNameAdd.error = "Enter name fully"
                isValid = false
            }

            for(user in userList){
                if(user.email == email){
                    isValid = false
                    binding.emailReg.error = "User with this email is already exists"
                }
            }

                if(isValid){
                    userList.add(User(email, fullName))
                    binding.fullNameAdd.setText("")
                    binding.emailReg.setText("")
                    userCounterSetUp()
                }

        }
    }
    fun getUserSetUp(){
        val btnGetUser = binding.btnGetUser
        btnGetUser.setOnClickListener {
            val email = binding.emailGet.text.toString().trim()
            var userToFind: User? = null
            var isValid = true
            for(user in userList){
                if(user.email == email){
                    userToFind = user
                }
            }
            if(email.isEmpty()){
                binding.emailGet.error = "Email is required"
                isValid = false

            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailGet.error = "Enter valid email"
                isValid = false
            }
            if(isValid){
                if(userToFind == null){
                    binding.userInfo.text = "User not found"
                }else{
                    val str = "${userToFind.email} \n ${userToFind.fullName}"
                    binding.userInfo.text = str
                    binding.emailGet.setText("")
                }
            }

        }


    }

    fun userCounterSetUp(){
        val str =  "Users -> ${userList.size}"
        binding.userCounter.text = str
    }

}