package ru.netology

class User(
    val id: Int = 0,
    val name: String,
    val readedNotes: MutableList<Pair<Int, Int>> = mutableListOf() //прочитанные сообщения
){ }
