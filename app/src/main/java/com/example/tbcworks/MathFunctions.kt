package com.example.tbcworks

class MathFunctions {

    fun convertEnglish(n: Int): String {
        val ones = arrayOf(
            "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen",
            "sixteen", "seventeen", "eighteen", "nineteen"
        )


        val tens = arrayOf(
            "", "", "twenty", "thirty", "forty", "fifty",
            "sixty", "seventy", "eighty", "ninety"
        )


        return when {
            n == 1000 -> "one thousand"
            n >= 100 -> {
                val hundreds = ones[n / 100] + " hundred"
                val rem = n % 100
                if (rem == 0) hundreds
                else hundreds + " " + convertEnglish(rem)
            }
            n >= 20 -> {
                val tensIndex = tens[n / 10]
                val rem = n % 10
                if (rem == 0) tensIndex
                else tensIndex + "-" + ones[rem]
            }
            else -> ones[n]
        }


    }
    fun convertGeorgian(n: Int): String {
        val ones = arrayOf(
            "", "ერთი", "ორი", "სამი", "ოთხი", "ხუთი", "ექვსი", "შვიდი", "რვა", "ცხრა",
            "ათი", "თერთმეტი", "თორმეტი", "ცამეტი", "თოთხმეტი", "თხუთმეტი",
            "თექვსმეტი", "ჩვიდმეტი", "თვრამეტი", "ცხრამეტი"
        )


        val twenties = arrayOf(
            "", "ოც", "ორმოც", "სამოც", "ოთხმოც",
        )

        val hundreds = arrayOf(
            "", "ას", "ორას", "სამას", "ოთხას", "ხუთას", "ექვსას", "შვიდას", "რვაას", "ცხრაას"
        )


        return when {
            n == 1000 -> "ათასი"
            n >= 100 -> {
                val hun = hundreds[n / 100]
                val rem = n % 100
                if (rem == 0) hun + "ი"
                else hun + convertGeorgian(rem)
            }
            n >= 20 -> {
                val tensIndex = twenties[n / 20]
                val rem = n % 20
                if (rem == 0) tensIndex + "ი"
                else tensIndex + "და" + ones[rem]
            }
            else -> ones[n]
        }
    }
}