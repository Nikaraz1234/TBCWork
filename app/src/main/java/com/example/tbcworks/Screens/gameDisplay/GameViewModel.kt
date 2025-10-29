package com.example.tbcworks.Screens.gameDisplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val _cells = MutableStateFlow<List<Cell>>(emptyList())
    val cells: StateFlow<List<Cell>> = _cells

    private val _winner = MutableStateFlow<String?>(null)
    val winner: StateFlow<String?> = _winner

    private val _currentPlayer = MutableStateFlow("X")
    val currentPlayer: StateFlow<String> = _currentPlayer

    private var rows = 0
    private var cols = 0

    fun startGame(rowCount:Int, colCount:Int){
        rows = rowCount
        cols = colCount
        _currentPlayer.value = PLAYER_X
        _winner.value = null
        _cells.value = List(rows * cols) { i -> Cell(row = i / cols, col = i % cols) }
    }

    fun onCellClicked(cell: Cell){
        if(cell.tag != "" || _winner.value != null){
            return
        }
        viewModelScope.launch {
            val updated = _cells.value.toMutableList()
            val index = updated.indexOfFirst { it.row == cell.row && it.col == cell.col }
            updated[index] = cell.copy(tag = _currentPlayer.value)
            _cells.value = updated
            if (checkWin(updated)) {
                _winner.value = _currentPlayer.value
            } else if (updated.all { it.tag.isNotEmpty() }) {
                _winner.value = "Friendship"
            } else {
                _currentPlayer.value = if (_currentPlayer.value == "X") "O" else "X"
            }
        }

    }

    fun checkWin(cells: List<Cell>) : Boolean{
        val board = Array(rows) { r ->
            Array(cols) { c ->
                cells[r * cols + c].tag
            }
        }

        for (r in 0 until rows) {
            if (board[r].all { it == _currentPlayer.value })
                return true
        }
        for (c in 0 until cols){
            if ((0 until rows).all { board[it][c] == _currentPlayer.value })
                return true
        }

        if (rows == cols) {
            if ((0 until rows).all { board[it][it] == _currentPlayer.value })
                return true
            if ((0 until rows).all { board[it][cols - 1 - it] == _currentPlayer.value })
                return true
        }

        return false

    }

    companion object {
        val PLAYER_X = "X"
        val PLAYER_O = "O"
    }

}