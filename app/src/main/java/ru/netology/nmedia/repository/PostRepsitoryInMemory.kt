package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository{
    private var posts = listOf(
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интен...",
            published = "21 мая 18:36",
            likes = 10,
            shares = 1_199_999,
            views = 100_100,
            likedByMe = false
        ),
        Post(id = 2,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Сегодня мы разберем, как пройти регестрацию...",
            published = "10 марта 18:36",
            likes = 1,
            shares = 567,
            views = 10,
            likedByMe = false
        ),
        Post(id = 3,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Как вы можете записаться на курсы. Вам нужно...",
            published = "10 марта 18:36",
            likes = 100_000,
            shares = 100_111,
            views = 10,
            likedByMe = false
        ),
        Post(id = 4,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Новой темой станет ново введение в...",
            published = "10 марта 18:36",
            likes = 10_001,
            shares = 10_000,
            views = 1000,
            likedByMe = false
        ),
        Post(id = 5,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "В данном посте мы обсудим идею...",
            published = "10 марта 18:36",
            likes = 100,
            shares = 10,
            views = 20,
            likedByMe = false
        )
    )
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data
    override fun likeById(id: Int) {
        posts = posts.map{
            if (it.id == id) it.copy(likedByMe = !it.likedByMe,
                likes = if(!it.likedByMe) it.likes + 1 else it.likes - 1) else it
        }
        data.value = posts
    }

    override fun shareById(id: Int) {
        data.value = posts.map{
            if (it.id == id) it.copy(shares = it.shares + 1) else it
        }
    }
}