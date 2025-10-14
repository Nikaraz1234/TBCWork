package com.example.tbcworks

import java.io.Serializable

data class User(var firstName:String, var lastName:String, var age:Int, var email: String) : Serializable{
}