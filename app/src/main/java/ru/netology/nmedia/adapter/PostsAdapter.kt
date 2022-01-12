package ru.netology.nmedia.adapter

import androidx.recyclerview.widget.DiffUtil
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.dto.logekForNumbers
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.netology.nmedia.databinding.FragmentCardPostBinding
import ru.netology.nmedia.dto.Post

interface OnActionListener {
    fun onRedact(post: Post) = Unit
    fun onRemove(post: Post) = Unit
    fun onVideo(post: Post) = Unit
    fun onLike(post: Post) = Unit
    fun onShare(post: Post) = Unit
    fun onPost(post: Post) = Unit
}

class PostsAdapter(private val onActionListener: OnActionListener)
    : ListAdapter<Post, PostViewHolder>(PostDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = FragmentCardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onActionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: FragmentCardPostBinding,
    private val onActionListener: OnActionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            iconForLike.text = logekForNumbers(post.likes)
            iconForShare.text = logekForNumbers(post.shares)
            iconForViews.text = logekForNumbers(post.views)
            iconForLike.isChecked = post.likedByMe
            if (post.urlVideo == " "){
                videoAndButton.visibility = View.GONE
            }
            image.setOnClickListener {
                onActionListener.onVideo(post)
            }
            buttonPlay.setOnClickListener{
                onActionListener.onVideo(post)
            }
            iconForLike.setOnClickListener {
                onActionListener.onLike(post)
            }
            iconForShare.setOnClickListener {
                onActionListener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.optoins_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onActionListener.onRemove(post)
                                true
                            }
                            R.id.redact -> {
                                onActionListener.onRedact(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
            author.setOnClickListener{
                onActionListener.onPost(post)
            }
            published.setOnClickListener{
                onActionListener.onPost(post)
            }
            content.setOnClickListener{
                onActionListener.onPost(post)
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}