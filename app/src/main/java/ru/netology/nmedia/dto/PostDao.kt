package ru.netology.nmedia.dto

import androidx.lifecycle.LiveData

interface PostDao{
    fun getAll(): List<Post>
    fun likeById(id: Int)
    fun shareById(id: Int)
    fun removeById(id: Int)
    fun save(post:Post): Post
    fun redact(post: Post)
}