package com.example.tbcworks

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcworks.databinding.ActivityRegister2Binding
import com.example.tbcworks.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class Register2 : AppCompatActivity() {

    private lateinit var binding: ActivityRegister2Binding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegister2Binding.inflate(layoutInflater)
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

        val gmail = intent.getStringExtra("gmail")!!
        val password = intent.getStringExtra("password")!!
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {

            if(binding.etUsername.text.isNullOrEmpty()){
                Snackbar.make(binding.root, "Enter Username!", Snackbar.LENGTH_SHORT).show()
            }else{
                registerUser(gmail, password)
            }
        }
    }

    fun registerUser(gmail: String, password: String){
        auth.createUserWithEmailAndPassword(gmail, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful()){
                    val user = auth.currentUser
                    val addUsername = userProfileChangeRequest {
                        displayName = binding.etUsername.text.toString()
                    }
                    user?.updateProfile(addUsername)?.addOnCompleteListener { t ->
                        if(t.isSuccessful){
                            Snackbar.make(binding.root, "Registered Successfully!", Snackbar.LENGTH_SHORT).show()
                        }
                    }


                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                }else{
                    Snackbar.make(binding.root, "Registration failed", Snackbar.LENGTH_LONG).show()
                }
            }

    }
}