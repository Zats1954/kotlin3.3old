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
            if (chat.ownerId == user.id || chat.guestId == user.id) { // чат отслеживает user
                    for(note in chat.notes.notes){
                       if (!user.readedNotes.contains(Pair(chat.id,note.id)) && note.ownerId!=user.id ){
                           countUnreadChat++
                           break}
                    }
            }
        }
        return countUnreadChat
    }

    fun getChats(user:User):List<String>{
        val list: MutableList<String> = mutableListOf()
        // чаты пользователя
        val userChat = chats.chats.filter{user.id in (listOf(it.guestId, it.ownerId))}
        //чаты с непустыми чужими сообщениями
        userChat.filter{it.notes.notes.filter{note:Note -> note.ownerId!=user.id}.isNotEmpty()}

        .forEach {chat:Chat ->if(chat.notes.notes.size == 0) {list.add(chat.title + " нет сообщений")}
                             else {list.add(chat.title)} }
        return list
    }

    fun chatNotes(user:User,chatId: Int): String {
        var info = "chat ${chatId.toString()}"                   // id чата
        chats.chats[chatId].notes.notes.filter{!user.readedNotes.contains(Pair(chatId, it.id))}
            .sortedBy { it.id }
            .let {
                if (!it.isEmpty()) {
                    // id последнего сообщения, начиная с которого нужно подгрузить новые
                    info += "\nпервое непрочитанное сообщение " + it.first().id
                    it.forEach { note: Note -> readNote(user.id, chatId, note.id) } //автоматически считаются прочитанными
                } else {info += "\nнепрочитанных сообщений нет"}
             }
        info += "\nвсего сообщений " + chats.chats[chatId].notes.notes.size           //  количество сообщений
        return info}
    }