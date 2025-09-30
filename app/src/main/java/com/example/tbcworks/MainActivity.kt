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
        setUpSave()
        setUpClear()
        setUpAgain()
    }

    fun setUpAgain(){
        val hiddenLayout = binding.hiddenLayout
        val btnAgain = binding.btnAgain

        hiddenLayout.visibility = View.GONE

        btnAgain.setOnClickListener {
            binding.inputsForm.visibility = View.VISIBLE
            binding.btnsForm.visibility = View.VISIBLE
            hiddenLayout.visibility = View.GONE
        }
    }
    fun setUpClear(){
        val btnClear = binding.btnClear


        btnClear.setOnClickListener {
            binding.emailInput.text?.clear()
            binding.firstNameInput.text?.clear()
            binding.lastNameInput.text?.clear()
            binding.usernameInput.text?.clear()
            binding.ageInput.text?.clear()
        }
    }
    fun setUpSave(){

        binding.btnSave.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val username = binding.usernameInput.text.toString().trim()
            val firstName = binding.firstNameInput.text.toString().trim()
            val lastName = binding.lastNameInput.text.toString().trim()
            val age = binding.ageInput.text.toString().trim()

            var isValid = true

            if (email.isEmpty()) {
                binding.emailInput.error = "Email is required"
                isValid = false
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailInput.error = "Enter valid email"
                isValid = false
            }

            if (username.isEmpty()) {
                binding.usernameInput.error = "Username is required"
                isValid = false
            } else if (username.length < 10) {
                binding.usernameInput.error = "Username length should be at least 10 characters"
                isValid = false
            }

            if (firstName.isEmpty()) {
                binding.firstNameInput.error = "First name is required"
                isValid = false
            }

            if (lastName.isEmpty()) {
                binding.lastNameInput.error = "Last name is required"
                isValid = false
            }

            if (age.isEmpty()) {
                binding.ageInput.error = "Age is required"
                isValid = false
            } else if (age.toIntOrNull() ?: -1 < 0) {
                binding.ageInput.error = "Age cannot be negative"
                isValid = false
            }

            if (isValid) {
                binding.inputsForm.visibility = View.GONE
                binding.btnsForm.visibility = View.GONE
                binding.hiddenLayout.visibility = View.VISIBLE
                binding.infoEmail.text = "Email: $email"
                binding.infoUsername.text = "Username: $username"
                binding.infoName.text = "Full Name: $firstName $lastName"
                binding.infoAge.text = "Age: $age"
            }
        }

    }
}