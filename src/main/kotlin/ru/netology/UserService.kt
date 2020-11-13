package ru.netology

class UserService( val chats: ChatService ) {
    val readNotes: Set<Pair<Int, Int>> = mutableSetOf()
    var users: MutableList<User> = mutableListOf<User>()

//    fun nextUnread(chatId: Int): Int? {
//        return readNotes[chatId]
//    }

    fun readNote(userId: Int, chatId: Int, noteId: Int) {
       if(users[userId].readedNotes.find { it.first == chatId && it.second == noteId } == null)
             users[userId].readedNotes.add(Pair(chatId, noteId))
    }


    fun getUnreadChatsCount(user: User): Int {
        var countUnreadChat = 0
        chats.chats.forEach { chat: Chat ->
            if (chat.ownerId == user.id || chat.gastId == user.id) { // чат отслеживает user
                    for(note in chat.notes.notes){
                       if (!readNotes.contains(Pair(chat.id,note.id)) && note.ownerId!=user.id ){
                           countUnreadChat++
                           break}
                    }
            }
        }
        return countUnreadChat
    }

    fun getChats(user:User):List<String>{
        var list: MutableList<String> = mutableListOf()
        // чаты пользователя
        val userChat = chats.chats.filter{user.id in (listOf(it.gastId, it.ownerId))}
        //чаты с непустыми чужими сообщениями
        userChat.filter{it.notes.notes.filter{note:Note -> note.ownerId!=user.id}.isNotEmpty()}

        .forEach {chat:Chat ->if(chat.notes.notes.size == 0) {list.add(chat.title + " нет сообщений")}
                             else {list.add(chat.title)} }
        return list
    }

    fun chatNotes(user:User,chatId: Int): String {
        var info: String = chatId.toString()
        chats.notes.notes.filter{!readNotes.contains(Pair(chatId, it.id))}
            .sortedBy { it.id }
            .let{info += "\n" + it.first().id
                info += "\n" + it.size
                it.forEach{note:Note -> readNote(user.id,chatId,note.id)}} //автоматически считаются прочитанными
        return info}
    }