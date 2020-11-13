package ru.netology

class Chat(val id: Int = 0,
           val ownerId: Int =0,
           var guestId: Int = 0,
           val title: String = "",
//           val notes : MutableList<Note> = mutableListOf<Note>()
           val notes:NoteService = NoteService()
) { }
