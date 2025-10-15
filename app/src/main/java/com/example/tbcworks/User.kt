package com.example.tbcworks

import java.io.Serializable

data class User(val id: Int,
                val firstName:String,
                val lastName:String,
                val birthday:String,
                val address:String,
                val email: String,
                var desc: String = "") : Serializable{
}