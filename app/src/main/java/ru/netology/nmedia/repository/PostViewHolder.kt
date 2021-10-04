package ru.netology.nmedia.repository

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.logekForNumbers

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesCount.text = logekForNumbers(post.likes)
            sharesCount.text = logekForNumbers(post.shares)
            viewsCount.text = logekForNumbers(post.views)
            iconForLike.setImageResource(
                if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
            )
            iconForLike.setOnClickListener {
                onLikeListener(post)
            }
            iconForShare.setOnClickListener {
                onShareListener(post)
            }
        }
    }
}