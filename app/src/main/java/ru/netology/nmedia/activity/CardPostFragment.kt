package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.contentArg
import ru.netology.nmedia.databinding.FragmentCardPostBinding
import ru.netology.nmedia.dto.logekForNumbers
import ru.netology.nmedia.viewModel.PostViewModel

class CardPostFragment: Fragment() {
    companion object{
        private const val POST_KEY = "POST_KEY"
        var Bundle.postArg: Int
            set(value) = putInt(POST_KEY, value)
            get() = getInt(POST_KEY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCardPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by activityViewModels()

        val postId = arguments?.postArg
        if (postId != null) {
            viewModel.postById(postId).observe(viewLifecycleOwner){ post ->
                post ?: run{
                    findNavController().navigateUp()
                    return@observe
                }
                with(binding) {
                    author.text = post.author
                    published.text = post.published
                    content.text = post.content
                    iconForLike.text = logekForNumbers(post.likes)
                    iconForShare.text = logekForNumbers(post.shares)
                    iconForViews.text = logekForNumbers(post.views)
                    iconForLike.isChecked = post.likedByMe
                    if (post.urlVideo == "") {
                        videoAndButton.visibility = View.GONE
                    }
                    iconForLike.setOnClickListener {
                        viewModel.likeById(postId)
                    }
                    iconForShare.setOnClickListener {
                        val intent = Intent().apply{
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, post.content)
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(intent, getString(R.string.icon_for_share))
                        startActivity(shareIntent)
                        viewModel.shareById(postId)
                    }

                    videoAndButton.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.urlVideo))
                        val videoIntent = Intent.createChooser(intent, getString(R.string.video))
                        startActivity(videoIntent)
                    }

                    menu.setOnClickListener {
                        PopupMenu(it.context, it).apply {
                            inflate(R.menu.optoins_post)
                            setOnMenuItemClickListener { item ->
                                when (item.itemId) {
                                    R.id.remove -> {
                                        viewModel.removeById(post.id)
                                        findNavController().navigateUp()
                                        true
                                    }
                                    R.id.redact -> {
                                        viewModel.redact(post)
                                        findNavController().navigate(R.id.action_cardPostFragment_to_newPostFragment, Bundle()
                                            .apply {
                                                contentArg = post.content
                                            })
                                        true
                                    }
                                    else -> false
                                }
                            }
                        }.show()
                    }
                }
            }
        }
        return binding.root
    }

}