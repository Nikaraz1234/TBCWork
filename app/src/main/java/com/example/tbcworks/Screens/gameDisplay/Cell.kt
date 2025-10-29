package com.example.tbcworks.Screens.gameDisplay

data class Cell(
    val row: Int,
    val col: Int,
    var tag: String = ""
){
    val id: Int = row * 100 + col
}
