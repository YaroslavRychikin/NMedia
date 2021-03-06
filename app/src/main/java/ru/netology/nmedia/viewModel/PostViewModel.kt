package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryFileImpl
import ru.netology.nmedia.repository.PostRepositorySQLiteImpl
import ru.netology.nmedia.repository.PostRepositorySharePrefsImpl

private val empty = Post()

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao)
    val data = repository.getAll()
    val _edited = MutableLiveData(empty)
    fun likeById(id: Int) = repository.likeById(id)
    fun shareById(id: Int) = repository.shareById(id)
    fun removeById(id: Int) = repository.removeById(id)
    fun changeContent(editContent: String) {
        _edited.value?.let {
            val text = editContent.trim()
            if (it.content == text) {
                return
            }
            _edited.value = it.copy(content = text)
        }

    }

    fun save() {
        _edited.value?.let {
            repository.save(it)
            _edited.value = empty
        }
    }

    fun redact(post: Post) {
        _edited.value = post
    }

    fun postById(id: Int): LiveData<Post?> = data.map{ posts ->
        posts.find{it.id == id }
    }
}