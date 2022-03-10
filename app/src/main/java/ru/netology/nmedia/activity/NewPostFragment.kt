package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.viewModel.PostViewModel
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.utils.StringArg

class NewPostFragment : Fragment() {
    companion object{
        var Bundle.contentArg by StringArg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by activityViewModels()


        binding.cancel.visibility = View.GONE
        binding.editContent.requestFocus()
        arguments?.contentArg?.also{
            binding.editContent.setText(it)
            binding.cancel.visibility = View.VISIBLE
        }
        binding.cancel.setOnClickListener {
            binding.cancel.visibility = View.GONE
            findNavController().navigateUp()
        }
        binding.save.setOnClickListener{
            val content = binding.editContent.text
            if (!content.isNullOrBlank()) {
                viewModel.changeContent(content.toString())
                viewModel.save()
                AndroidUtils.hideKeyboard(binding.root)
            }
            findNavController().navigateUp()
        }

        return binding.root
    }
}