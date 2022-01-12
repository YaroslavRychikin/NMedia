package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryFileImpl(
    private val context: Context,
) : PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"


    private var posts = listOf(
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интен...",
            published = "1 марта 18:36",
            likes = 10,
            shares = 1_199_999,
            views = 100_100,
            likedByMe = false
        ),
        Post(id = 2,
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

    init {
        val file = context.filesDir.resolve(filename)
        if(file.exists()){
            context.openFileInput(filename).bufferedReader().use{
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else {
            sync()
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Int) {
        posts = posts.map{
            if (it.id == id) it.copy(likedByMe = !it.likedByMe,
                likes = if(!it.likedByMe) it.likes + 1 else it.likes - 1) else it
        }
        data.value = posts
        sync()
    }


    override fun shareById(id: Int) {
        posts = posts.map{
            if (it.id == id) it.copy(shares = it.shares + 1) else it
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Int) {
        posts = posts.filter{it.id != id }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if (post.id == 0) {
            posts = listOf(
                post.copy(
                    id = posts.lastOrNull()?.id?.inc() ?: 1,
                    likedByMe = false,
                )
            ) + posts
            data.value = posts
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }

    override fun redact(post: Post){}

    private fun sync(){
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use{
            it.write(gson.toJson(posts))
        }
    }
}