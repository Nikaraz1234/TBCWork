package com.example.tbcworks

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editText = findViewById<AppCompatEditText>(R.id.editText)
        val btnSubmit = findViewById<AppCompatButton>(R.id.btn)
        val txtView = findViewById<AppCompatTextView>(R.id.textView)
        val languageToggle = findViewById<AppCompatToggleButton>(R.id.languageToggle)

        val math: MathFunctions = MathFunctions()

        btnSubmit.setOnClickListener {
            val input = editText.text.toString()
            if(input.isNotEmpty()){
                val num = input.toIntOrNull()
                if(num == null){
                    txtView.text = if(languageToggle.isChecked){
                        "Enter valid number"
                    }else{
                        "შეიყვანე რიცხვი"
                    }
                }else{
                    if(1 <= num && num <= 1000){

                        val result = if(languageToggle.isChecked) {
                            math.convertEnglish(num)
                        }else{
                            math.convertGeorgian(num)
                        }
                        txtView.text = result
                    }else{
                        txtView.text = if(languageToggle.isChecked){
                            "Enter number between 1 and 1000"
                        }else{
                            "რიცხვი უნდა იყოს 1-დან 1000-მდე"
                        }
                    }
                }
            }else{
                txtView.text = if(languageToggle.isChecked){
                    "Enter value"
                }else{
                    "შეიყვანეთ რაიმე რიცხვი"
                }
            }
        }
    }
}