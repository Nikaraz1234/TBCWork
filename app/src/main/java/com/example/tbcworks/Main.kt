package com.example.tbcworks

import kotlin.random.Random

fun factorial(n: Int): Long{
    var result = 1L
    for(i in 1..n){
        result *= i
    }
    return result
}

fun main() {
    println("Start")
    var repeat = true
    while(repeat){
        println("Enter first value:")
        val x = readLine()
        println("Now enter second value:")
        val y = readLine()
        println("Enter Operation Type:\n1. /\n2. *\n3. %\n4. !")
        val operation = readLine()

        val xNum = x?.filter { it.isDigit() }?.toIntOrNull() ?: Random.nextInt(-127, 129)
        val yNum = y?.filter { it.isDigit() }?.toIntOrNull() ?: Random.nextInt(-127, 129)

        when(operation){
            "1", "/" -> if(yNum != 0) println("$xNum / $yNum = ${xNum / yNum}") else println("Cannot divide by zero")
            "2", "*" -> println("$xNum * $yNum = ${xNum * yNum}")
            "3", "%"-> if(yNum != 0) println("$xNum % $yNum = ${xNum % yNum}") else println("Cannot modulo by zero")
            "4", "!"->{
                val ans = xNum / yNum
                if(ans >= 0) println("($xNum / $yNum)! = ${factorial(ans)}")
                else println("Factorial not defined for negative numbers")
            }
            else -> throw IllegalArgumentException("Wrong Operation")
        }
        var validAnswer = false
        while (!validAnswer) {
            println("Do you want to repeat?\ny - Yes\nn - No")
            val answer = readLine()?.trim()?.lowercase()

            when (answer) {
                "n", "no" -> {
                    repeat = false
                    validAnswer = true
                }
                "y", "yes" -> {
                    repeat = true
                    validAnswer = true
                }
                else -> println("Wrong input! Please enter 'y' or 'n'")
            }
        }

        println("End")
    }





}
