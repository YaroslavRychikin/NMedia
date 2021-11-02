package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cancel.visibility = View.GONE
        val text = intent?.getStringExtra(Intent.EXTRA_TEXT)
        with(binding.editContent){
            requestFocus()
            if (!text.isNullOrBlank()) {
                setText(text)
                binding.cancel.visibility = View.VISIBLE
            } else {
                setText("")
            }
        }
        binding.cancel.setOnClickListener {
            binding.cancel.visibility = View.GONE
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        binding.save.setOnClickListener{
            val content = binding.editContent.text?.toString()
            if (content.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED)
            } else {
                val intent = Intent()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}