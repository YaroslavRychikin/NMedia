package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository{
    private var post = Post(id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интен...",
        published = "21 мая 18:36",
        likes = 10,
        shares = 1_199_999,
        views = 100_100,
        likedByMe = false
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe,
            likes = if(!post.likedByMe) post.likes + 1 else post.likes - 1
        )
        data.value = post
    }

    override fun share() {
        post = post.copy(shares = post.shares + 1)
        data.value = post
    }
}