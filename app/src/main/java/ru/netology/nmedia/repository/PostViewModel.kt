package ru.netology.nmedia.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post

private val empty = Post()

class PostViewModel : ViewModel(){
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val _edited = MutableLiveData(empty)
    fun likeById(id: Int) = repository.likeById(id)
    fun shareById(id: Int) = repository.shareById(id)
    fun removeById(id: Int) = repository.removeById(id)
    fun changeContent(editContent: String) {
        _edited.value?.let{
            val text = editContent.trim()
            if (it.content == text){
                return
            }
            _edited.value = it.copy(content = text)
        }

    }
    fun save(){
        _edited.value?.let {
            repository.save(it)
            _edited.value = empty
        }
    }

    fun redact(post: Post) {
        _edited.value = post
    }
}