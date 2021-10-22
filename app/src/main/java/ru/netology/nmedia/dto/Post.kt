package ru.netology.nmedia.dto
data class Post(val id:Int = 0,
           val author:String = "Me",
           val content:String = " ",
           val published:String = "now",
           val likes:Int = 0,
           val shares:Int = 0,
           val views:Int = 0,
           val likedByMe:Boolean = false
)
fun logekForNumbers(number:Int) = when(number) {
    in 1100..9999 ->  "${number / 1000}.${number / 100 % 10} K"
    in 10_000..999_999, in 1000..1099 -> "${number/1000}K"
    in  1_000_000..999_999_999 -> "${number / 1_000_000}.${number / 100_000 % 10}M"
    else -> "$number"
}