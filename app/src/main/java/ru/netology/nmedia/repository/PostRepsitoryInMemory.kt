package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository{
    private var nextId = 1
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интен...",
            published = "1 марта 18:36",
            likes = 10,
            shares = 1_199_999,
            views = 100_100,
            likedByMe = false
        ),
        Post(id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Сегодня мы разберем, как пройти регестрацию...",
            published = "2 марта 18:36",
            likes = 1,
            shares = 567,
            views = 10,
            likedByMe = false
        ),
        Post(id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Как вы можете записаться на курсы. Вам нужно...",
            published = "15 марта 18:36",
            likes = 100_000,
            shares = 100_111,
            views = 10,
            likedByMe = false
        ),
        Post(id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Новой темой станет ново введение в...",
            published = "20 марта 18:36",
            likes = 10_001,
            shares = 10_000,
            views = 1000,
            likedByMe = false
        ),
        Post(id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "В данном посте мы обсудим идею...",
            published = "30 марта 18:36",
            urlVideo = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
            likes = 100,
            shares = 10,
            views = 20,
            likedByMe = false
        )
    ).reversed()
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data
    override fun likeById(id: Int) {
        data.value = data.value?.map{
            if (it.id == id) it.copy(likedByMe = !it.likedByMe,
                likes = if(!it.likedByMe) it.likes + 1 else it.likes - 1) else it
        }
    }


    override fun shareById(id: Int) {
        data.value = data.value?.map{
            if (it.id == id) it.copy(shares = it.shares + 1) else it
        }
    }

    override fun removeById(id: Int) {
        data.value = data.value?.filter{it.id != id }
    }

    override fun save(post: Post) {
        if (post.id == 0) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    likedByMe = false,
                )
            ) + posts
            data.value = posts
            return
        }

        data.value = data.value?.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
    }

    override fun redact(post: Post){}
}