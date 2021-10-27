package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.adapter.OnActionListener
import ru.netology.nmedia.repository.PostViewModel
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.utils.AndroidUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter (object : OnActionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onRedact(post: Post) {
                binding.cancel.visibility = View.VISIBLE
                viewModel.redact(post)
            }
        }  )
        binding.listOfPosts.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        viewModel.edited.observe(this) { post ->
            if(post.id == 0) {
                return@observe
            }
            with(binding.editContent) {
                requestFocus()
                setText(post.content)
            }
        }
        binding.cancel.visibility = View.GONE

        binding.cancel.setOnClickListener { can ->
            can.visibility = View.GONE
            with(binding.editContent) {
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
        binding.save.setOnClickListener {
            with(binding.editContent){
                if (text.isNullOrBlank()) {
                    Toast.makeText(this@MainActivity, "Content must not be empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(text.toString())
                viewModel.save()
                binding.cancel.visibility = View.INVISIBLE
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
    }
}
