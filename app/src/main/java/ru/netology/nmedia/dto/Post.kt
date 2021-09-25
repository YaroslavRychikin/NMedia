package ru.netology.nmedia.dto
class Post(id:Int,
           val author:String,
           val content:String,
           val published:String,
           var likes:Int,
           var shares:Int,
           val views:Int,
           var likedByMe:Boolean = false
)
fun logekForNumbers(number:Int) = when(number) {
    in 1100..9999 ->  "${number / 1000}.${number / 100 % 10} K"
    in 10_000..999_999, in 1000..1099 -> "${number/1000}K"
    in  1_000_000..999_999_999 -> "${number / 1_000_000}.${number / 100_000 % 10}M"
    else -> "$number"
}