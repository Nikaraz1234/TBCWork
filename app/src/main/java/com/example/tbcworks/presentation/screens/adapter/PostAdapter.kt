package com.example.tbcworks.presentation.screens.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tbcworks.R
import com.example.tbcworks.databinding.ItemPostLayoutBinding
import com.example.tbcworks.presentation.screens.model.PostModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostAdapter :
    ListAdapter<PostModel, PostAdapter.PostViewHolder>(DIFF_CALLBACK) {

    inner class PostViewHolder(
        private val binding: ItemPostLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PostModel) = with(binding) {

            tvName.text = item.firstName.plus(SPACE).plus(item.lastName)
            tvDescription.text = item.postDesc

            val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            tvDate.text = dateFormat.format(Date(item.postDate)).lowercase()

            tvComments.text = item.commentsCount.toString().plus(COMMENTS)
            tvLikes.text = item.likesCount.toString().plus(LIKES)

            ivProfilePicture.load(item.avatar) {
                crossfade(true)
                placeholder(R.drawable.bg_edit_text)
                error(R.drawable.bg_edit_text)
            }

            etComment.isEnabled = item.canComment
            etComment.hint = if (item.canComment) COMMENT_HINT_ENABLED else COMMENT_HINT_DISABLED

            val images = item.images
            val imageViews = listOf(ivLeft, ivTopRight, ivBottomRight)

            if (images.isNullOrEmpty()) {
                imageContainer.visibility = View.GONE
            } else {
                when (images.size) {
                    1 -> {
                        ivLeft.visibility = View.VISIBLE
                        ivLeft.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                        ivLeft.load(images[0]) {
                            crossfade(true)
                            placeholder(R.drawable.bg_edit_text)
                            error(R.drawable.bg_edit_text)
                        }
                    }
                    2 -> {
                        ivLeft.visibility = View.VISIBLE
                        ivTopRight.visibility = View.VISIBLE
                        ivBottomRight.visibility = View.GONE

                        (ivLeft.layoutParams as LinearLayout.LayoutParams).weight = 1f
                        (ivTopRight.layoutParams as LinearLayout.LayoutParams).weight = 1f

                        images.take(2).forEachIndexed { index, url ->
                            when (index) {
                                0 -> ivLeft.load(url) {
                                    crossfade(true)
                                    placeholder(R.drawable.bg_edit_text)
                                    error(R.drawable.bg_edit_text)
                                }
                                1 -> ivTopRight.load(url) {
                                    crossfade(true)
                                    placeholder(R.drawable.bg_edit_text)
                                    error(R.drawable.bg_edit_text)
                                }
                            }
                        }

                    }
                    else -> {
                        images.take(3).forEachIndexed { index, url ->
                            imageViews[index].visibility = View.VISIBLE
                            imageViews[index].load(url) {
                                crossfade(true)
                                placeholder(R.drawable.bg_edit_text)
                                error(R.drawable.bg_edit_text)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private const val DATE_FORMAT = "d MMMM 'at' h:mm a"
        private const val COMMENT_HINT_ENABLED = "Write a comment..."
        private const val COMMENT_HINT_DISABLED = "Comments disabled"
        private const val COMMENTS = " Comments"
        private const val LIKES = " Likes"
        private const val SPACE = " "

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostModel>() {
            override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean =
                oldItem.postDate == newItem.postDate

            override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean =
                oldItem == newItem
        }
    }

}
