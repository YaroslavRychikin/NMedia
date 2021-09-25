package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.logekForNumbers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post(id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интен...",
            published = "21 мая 18:36",
            likes = 10000,
            shares = 1_199_999,
            views = 100_100
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            sharesCount.text = logekForNumbers(post.shares)
            viewsCount.text = logekForNumbers(post.views)
            if (post.likedByMe) {
                iconForLike.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                likesCount.text = logekForNumbers(++post.likes)
            } else {
                likesCount.text = logekForNumbers(post.likes)
            }
            iconForLike.setOnClickListener {
                post.likedByMe = !post.likedByMe
                iconForLike.setImageResource(
                    if (post.likedByMe) R.drawable.ic_baseline_favorite_24
                    else R.drawable.ic_baseline_favorite_border_24
                )
                if (post.likedByMe) likesCount.text = logekForNumbers(++post.likes)
                else likesCount.text = logekForNumbers(--post.likes)
            }
            iconForShare.setOnClickListener {
                sharesCount.text = logekForNumbers(++post.shares)
                Toast.makeText(this@MainActivity, "Shared", Toast.LENGTH_SHORT).show()
            }
        }
    }
}