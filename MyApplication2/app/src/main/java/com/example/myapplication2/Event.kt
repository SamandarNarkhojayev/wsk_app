package com.example.myapplication2

data class Event(
    val title: String,
    val imageUrl: String,
    val description: String,
    var status: String // "Прочитано" или "Непрочитано"
)

