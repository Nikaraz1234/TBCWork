package com.example.tbcworks

fun main(){
    val mathFunctions: MathFunctions = MathFunctions()

    println(mathFunctions.gcd(18, 24))
    println(mathFunctions.lcm(18, 24))

    println(mathFunctions.sumOfEven())

    println(mathFunctions.isPolyndrom("Anana"))

    println(mathFunctions.containSymbol("dsadda"))
    println(mathFunctions.containSymbol("dsadda$"))

    println(mathFunctions.reverseNumber(10220))

}