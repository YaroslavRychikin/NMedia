package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.CardPostFragment.Companion.postArg
import ru.netology.nmedia.activity.NewPostFragment.Companion.contentArg
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.adapter.OnActionListener
import ru.netology.nmedia.viewModel.PostViewModel
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by activityViewModels()

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
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment, Bundle()
                    .apply {
                    contentArg = post.content
                })
                viewModel.redact(post)
            }

            override fun onPost(post: Post) {
                findNavController().navigate(R.id.action_feedFragment_to_cardPostFragment, Bundle()
                    .apply{
                        postArg = post.id
                })
            }
        }  )

        binding.listOfPosts.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.newPost.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        return binding.root
    }
}