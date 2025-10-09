package com.example.tbcworks

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcworks.databinding.ActivityLoginBinding
import com.example.tbcworks.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
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

        binding.btnLogIn.setOnClickListener {
            LoginButton()
        }

    }

    fun validateLogin(gmail : String, password : String) : Boolean{

        return !gmail.isNullOrEmpty() && !password.isNullOrEmpty()
    }

    fun LoginButton(){
        val gmail = binding.etGmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if(!validateLogin(gmail, password)){
            Snackbar.make(binding.root, "Enter gmail and password", Snackbar.LENGTH_SHORT).show()
        }else{
            auth.signInWithEmailAndPassword(gmail, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        Snackbar.make(binding.root, "welcome ${user?.displayName}", Snackbar.LENGTH_SHORT).show()
                        binding.root.postDelayed({
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 1500)
                    }else{
                        Snackbar.make(binding.root, "Login failed", Snackbar.LENGTH_SHORT).show()
                    }
                }
        }

    }
}