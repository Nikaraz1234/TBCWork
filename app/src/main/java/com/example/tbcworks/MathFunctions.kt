package com.example.tbcworks

class MathFunctions {
    //greatest common divisor ეგრედწოდებული უსგ
    fun gcd(num1: Int, num2: Int) : Int{
        return if(num2 == 0) num1 else gcd(num2, num1 % num2)
    }
    //least common multiplier ეგრედწოდებული უსჯ
    fun lcm(num1: Int, num2: Int): Int{
        return (num1*num2) / gcd(num1, num2)
    }


    //შეიცავს თუ არა '$'-ამ სიმბოლოს
    fun containSymbol(string: String): Boolean{
        return string.contains('$')
    }

    //ლუწი რიცხვების ჯამი 100-მდე
    fun sumOfEven(num:Int = 100): Int{
        if ( num <= 0) return 0

        return if(num % 2 == 0){
            num + sumOfEven(num-2)
        }else{
            sumOfEven(num-1)
        }
    }

    //რიცხვის შემობრუნება
    fun reverseNumber(num: Int): Int{
        var n = num
        var reversedNum = 0

        while(n!=0){
            val x = n % 10
            n /= 10
            reversedNum = (reversedNum * 10) + x
        }

        return reversedNum
    }

    //არის თუ არა პოლინდრომი
    fun isPolyndrom(string: String) : Boolean{
        val loweredString = string.lowercase().filter { it.isLetterOrDigit() }
        return loweredString == loweredString.reversed()
    }
}