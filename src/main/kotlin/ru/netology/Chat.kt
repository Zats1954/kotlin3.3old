package ru.netology

class Chat(val id: Int = 0,
           val ownerId: Int =0,
           var gastId: Int = 0,
           val title: String = "",
//           val notes : MutableList<Note> = mutableListOf<Note>()
           val notes:NoteService = NoteService()
) { }
