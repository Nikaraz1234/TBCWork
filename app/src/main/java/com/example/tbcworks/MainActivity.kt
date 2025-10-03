package com.example.tbcworks

import android.os.Bundle
import android.util.Patterns
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
import com.example.tbcworks.databinding.ActivityMainBinding
import android.view.View

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var words: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        saveWords()
        outputAnagrams()
        setUpClear()
    }



    private fun saveWords() {
        val btnSave = binding.btnSave

        btnSave.setOnClickListener {
            val input = binding.anagramEditText.text.toString().trim()

            if (input.isEmpty()) {
                binding.anagramEditText.error = "Enter word"
            } else {

                if (!words.contains(input)) {
                    words.add(input)
                    binding.anagramEditText.setText("")
                } else {
                    binding.anagramEditText.error = "Word already added"
                }
            }
        }
    }

    private fun outputAnagrams() {
        val btnOutput = binding.btnOutput

        btnOutput.setOnClickListener {
            if (words.isNotEmpty()) {
                val grouped = groupAnagrams(words)
                binding.anagramTextView.text = grouped.joinToString("\n")
            } else {
                binding.anagramTextView.text = "No words added yet"
            }
        }
    }

    private fun groupAnagrams(words: List<String>): List<List<String>> {
        return words.groupBy { it.toCharArray().sorted().joinToString("") }
            .values
            .toList()
    }

    private fun setUpClear() {
        val btnClear = binding.btnClear

        btnClear.setOnClickListener {
            val inputEditText = binding.anagramEditText
            val anagramTextView = binding.anagramTextView

            inputEditText.setText("")
            anagramTextView.setText("")

            words.clear()
        }
    }
}
