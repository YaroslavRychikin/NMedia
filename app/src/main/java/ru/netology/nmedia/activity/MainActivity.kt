package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.adapter.OnActionListener
import ru.netology.nmedia.repository.PostViewModel
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.contracts.NewPostRedactContract
import ru.netology.nmedia.contracts.NewPostResultContract
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val newPostResultLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }
        val newPostRedactLauncher = registerForActivityResult(NewPostRedactContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }
        val adapter = PostsAdapter (object : OnActionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply{
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(intent, getString(R.string.icon_for_share))
                startActivity(shareIntent)
                viewModel.shareById(post.id)
            }

            override fun onVideo(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.urlVideo))
                val videoIntent = Intent.createChooser(intent, getString(R.string.video))
                startActivity(videoIntent)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onRedact(post: Post) {
                newPostRedactLauncher.launch(post.content)
                viewModel.redact(post)
            }
        }  )

        binding.listOfPosts.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.newPost.setOnClickListener {
            newPostResultLauncher.launch()
        }
    }
}