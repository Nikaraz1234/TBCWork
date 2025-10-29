package com.example.tbcworks.Screens.gameDisplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.CellLayoutBinding

class GameAdapter(
    private val onCellClicked: (Cell) -> Unit
) : ListAdapter<Cell, GameAdapter.CellViewHolder>(DIFFUTIL) {


    inner class CellViewHolder(private val binding: CellLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(cell: Cell) = with(binding){
            btnCell.apply {
                when (cell.tag){
                    PLAYER_X -> setImageResource(R.drawable.x_symbol)
                    PLAYER_O -> setImageResource(R.drawable.o_symbol)
                    else -> setImageDrawable(null)
                }

                isEnabled = cell.tag.isEmpty()



                setOnClickListener {
                    onCellClicked(cell)
                }
            }
        }
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameAdapter.CellViewHolder {
        val binding = CellLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CellViewHolder(binding)

    }

    override fun onBindViewHolder(holder: GameAdapter.CellViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        const val PLAYER_X = "X"
        const val PLAYER_O = "O"

        val DIFFUTIL = object : DiffUtil.ItemCallback<Cell>() {
            override fun areItemsTheSame(oldItem: Cell, newItem: Cell) = oldItem.id== newItem.id
            override fun areContentsTheSame(oldItem: Cell, newItem: Cell) = oldItem == newItem
        }
    }
}