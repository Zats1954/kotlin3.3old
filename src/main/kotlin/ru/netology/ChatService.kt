package ru.netology

class ChatService {
    var chats = mutableListOf<Chat>()
    var notes = NoteService()

    fun createChat(ownerId: Int,
                   name: String = "newChat"
    ): Chat {
        chats.add(Chat(id = chats.maxByOrNull { it.id }.let{if(it!= null) it.id +1 else 0} ,
                       ownerId = ownerId,
                       title = name))
        return chats.last()
    }

    fun addChatUser(chatId: Int, userId: Int){
        if(chats[chatId].ownerId != userId)
                 chats[chatId].guestId = userId
        else throw error("Owner can't write himself")
    }

    fun deleteChat(chatId: Int){
        chats.removeAll{it.id == chatId}
    }

    fun allChats() : List<Chat>{
        return chats
    }

    fun writeNote(userId: Int, chatId: Int, title: String, message: String) {
        chats.find { it.id == chatId }.let{it?.notes?.notes?.add(Note(it.notes.maxId() +1,ownerId = userId, title = title, text = message  ))}
    }
}