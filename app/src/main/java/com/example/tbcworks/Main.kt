package com.example.tbcworks

fun main() {
    val mathFunctions = MathFunctions()

    println("GCD of 18 and 24: ${mathFunctions.gcd(18, 24)}")
    println("LCM of 18 and 24: ${mathFunctions.lcm(18, 24)}")

    println("Sum of even numbers to 100: ${mathFunctions.sumOfEven()}")

    println(mathFunctions.isPolyndrom("lalalal"))
    println(mathFunctions.isPolyndrom("slalalal"))

    println(mathFunctions.containSymbol("dsadda$"))
    println(mathFunctions.containSymbol("dsadda"))

    val number = 10220
    println("Reversed number of $number: ${mathFunctions.reverseNumber(number)}")
}
