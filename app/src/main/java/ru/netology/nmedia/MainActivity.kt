package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.logekForNumbers
import ru.netology.nmedia.repository.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
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
            }
        }
        binding.iconForLike.setOnClickListener {
            viewModel.like()
        }
        binding.iconForShare.setOnClickListener {
            viewModel.share()
            Toast.makeText(this@MainActivity, "Shared", Toast.LENGTH_SHORT).show()
        }
    }
}