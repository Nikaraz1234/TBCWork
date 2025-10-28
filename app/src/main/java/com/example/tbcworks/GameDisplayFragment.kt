package com.example.tbcworks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.tbcworks.databinding.FragmentGameDisplayBinding
import com.google.android.material.snackbar.Snackbar

class GameDisplayFragment : BaseFragment<FragmentGameDisplayBinding>() {

    private val args: GameDisplayFragmentArgs by navArgs()
    private var rows = args.rows
    private var cols = args.cols
    private var currentPlayer = "X"
    private lateinit var buttons: Array<Array<ImageButton>>
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameDisplayBinding {
        return FragmentGameDisplayBinding.inflate(inflater, container, false)
    }



    override fun bind() = with(binding){

        rows = args.rows
        cols = args.cols

        glDisplay.rowCount = rows
        glDisplay.columnCount = cols

        buttons = Array(rows) { r ->
            Array(cols) { c ->
                val button = ImageButton(requireContext()).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0
                        height = 0
                        rowSpec = GridLayout.spec(r, 1f)
                        columnSpec = GridLayout.spec(c, 1f)
                        setMargins(8, 8, 8, 8)
                    }

                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                    adjustViewBounds = true
                }

                button.setOnClickListener { onClicked(button, r, c) }
                glDisplay.addView(button)
                button
            }
        }
    }

    private fun onClicked(button: ImageButton, row: Int, col: Int) {
        if (button.tag != null) return

        if (currentPlayer == "X") {
            button.setImageResource(R.drawable.x_symbol)
            button.tag = "X"
            currentPlayer = "O"
        } else {
            button.setImageResource(R.drawable.o_symbol)
            button.tag = "O"
            currentPlayer = "X"
        }

        button.isEnabled = false
        checkWin()
    }

    private fun checkWin(){
        val board = Array(rows) { r ->
            Array(cols) { c ->
                buttons[r][c].tag?.toString() ?: ""
            }
        }

        for (r in 0 until rows) {
            val row = board[r]
            if (row.all { it == "X" }) {
                showWinner("X"); return
            }
            if (row.all { it == "O" }) {
                showWinner("O"); return
            }
        }
        for (c in 0 until cols) {
            val col = Array(rows) { r -> board[r][c] }
            if (col.all { it == "X" }) {
                showWinner("X"); return
            }
            if (col.all { it == "O" }) {
                showWinner("O"); return
            }
        }
        if (rows == cols) {
            val diag1 = Array(rows) { i -> board[i][i] }
            if (diag1.all { it == "X" }) {
                showWinner("X"); return
            }
            if (diag1.all { it == "O" }) {
                showWinner("O"); return
            }

            val diag2 = Array(rows) { i -> board[i][cols - 1 - i] }
            if (diag2.all { it == "X" }) {
                showWinner("X"); return
            }
            if (diag2.all { it == "O" }) {
                showWinner("O"); return
            }
        }

        val allFilled = board.all { row -> row.all { it.isNotEmpty() } }
        if (allFilled) {
            showWinner("Friendship")
        }

    }

    private fun showWinner(winner: String){
        Snackbar.make(binding.root, "$winner wins!", Snackbar.LENGTH_SHORT).show()

        disableAll()

        binding.root.postDelayed({
            findNavController().popBackStack()
        }, 1000)
    }

    private fun disableAll(){
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                buttons[r][c].isEnabled = false
            }
        }
    }
}