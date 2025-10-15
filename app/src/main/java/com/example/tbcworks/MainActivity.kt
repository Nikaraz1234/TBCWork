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
import androidx.core.widget.addTextChangedListener
import com.example.tbcworks.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var users: MutableList<User> = mutableListOf(
        User(
            id = 1,
            firstName = "გრიშა",
            lastName = "ონიანი",
            birthday = "1724647601641",
            address = "სტალინის სახლმუზეუმი",
            email = "grisha@mail.ru"
        ),
        User(
            id = 2,
            firstName = "Jemal",
            lastName = "Kakauridze",
            birthday = "1714647601641",
            address = "თბილისი, ლილოს მიტოვებული ქარხანა",
            email = "jemal@mail.ru"
        ),
        User(
            id = 3,
            firstName = "Omeg",
            lastName = "Kakauridze",
            birthday = "1724647701641",
            address = "თბილისი, ასათიანი 18",
            email = "omger@gmail.ru"
        ),
        User(
            id = 32,
            firstName = "ბორის",
            lastName = "გარუჩავა",
            birthday = "1714947601641",
            address = "თბილისი, იაშვილი 14",
            email = ""
        ),
        User(
            id = 34,
            firstName = "აბთო",
            lastName = "სიხარულიძე",
            birthday = "1711947601641",
            address = "ფოთი",
            email = "tebzi@gmail.ru"
        )
    )

    private val userReciever = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if (result.resultCode == RESULT_OK){
            result.data?.let { data ->
                val user = data.getSerializableExtra("user") as? User
                if(user != null){
                    users.add(user)
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
        setUpListeners()
    }


    private fun setUpListeners() = with(binding){
        btnAddUser.setOnClickListener {
            addUser()
        }
        etFirstName.addTextChangedListener {
            searchUser()
        }
    }

    private fun searchUser() = with(binding){
        val searchText = etFirstName.text.toString().trim().lowercase()

        val user = users.firstOrNull(){ user ->
            val fullName = "${user.firstName.lowercase()} ${user.lastName.lowercase()}"
            user.desc = searchText
            val birthdayDate = convertMilliSecondsToDate(user.birthday).lowercase()

            fullName.contains(searchText) ||
                    user.firstName.lowercase().contains(searchText) ||
                    user.lastName.lowercase().contains(searchText) ||
                    birthdayDate.contains(searchText) ||
                    user.address.lowercase().contains(searchText) ||
                    user.email.lowercase().contains(searchText)
        }


        tvSearch.text = if(searchText.isEmpty()){
            ""
        }else if(user != null){
            "ID: ${user.id}"
        }else{
            "User not found"
        }

    }

    private fun convertMilliSecondsToDate(milliSeconds: String): String {
        val date = Date(milliSeconds.toLong())
        val format = SimpleDateFormat("MMMM dd", Locale.getDefault())
        return format.format(date)
    }

    private fun addUser(){
        val intent = Intent(this, UserManagmentActivity::class.java)
        val lastUserID = users.last().id
        intent.putExtra("userId", lastUserID)
        userReciever.launch(intent)
    }

}