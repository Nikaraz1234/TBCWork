package com.example.tbcworks

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcworks.databinding.ActivityLoginBinding
import com.example.tbcworks.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpButtons()


    }

    private fun setUpButtons(){

        binding.btnBack.setOnClickListener {
            val intent  = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnNext.setOnClickListener {
            val gmail = binding.etGmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if(validateRegister()){
                val intent = Intent(this, Register2::class.java)
                intent.putExtra("gmail", gmail)
                intent.putExtra("password", password)
                startActivity(intent)
            }
        }
    }

    fun validateRegister() : Boolean {
        val gmail = binding.etGmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        var isValid = true

        if(gmail.isEmpty()){
            binding.etGmail.error = "Gmail cannot be empty"
            isValid = false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(gmail).matches()){
            binding.etGmail.error = "Invalid gmail"
            isValid = false
        }

        if(password.isEmpty()){
            binding.etPassword.error = "Password cannot be empty"
            isValid = false
        }else if(password.length < 8){
            binding.etPassword.error = "password must be at least 8 characters"
            isValid = false
        }
        return isValid
    }



}