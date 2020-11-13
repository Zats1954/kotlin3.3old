package ru.netology

    fun main() {
        val chats = ChatService()
        val users = UserService(chats)
        users.users.add(User(0, "Миша"))
        users.users.add(User(1, "Вася"))
        users.users.add(User(2, "Игорь"))
        users.users.add(User(3, "Сергей"))

        chats.createChat(users.users[0].id, "cinema")
        chats.createChat(users.users[2].id, "theatr")
        chats.createChat(users.users[1].id, "sport")

        chats.chats.forEach {println(it.id.toString() + " " + it.title + " создал " +
                it.let{owner ->  users.users[owner.ownerId].name})}
        println()

        chats.addChatUser(0, 1)
        chats.addChatUser(1, 3)
        chats.addChatUser(2, 3)

        chats.writeNote(userId = users.users[0].id, 0, "заголовок " +  users.users[0].name ,"newMessageA")
        chats.writeNote(userId = users.users[1].id, 0, "ответ " +  users.users[1].name ,"сообщениеA")
        chats.writeNote(userId = users.users[2].id, 0, "ответ " +  users.users[2].name ,"сообщениеA")
        chats.writeNote(userId = users.users[3].id, 0, "ответ " +  users.users[3].name ,"сообщениеA")

        chats.writeNote(userId = users.users[1].id, 1, "заголовок " +  users.users[1].name ,"newMessageB")
        chats.writeNote(userId = users.users[2].id, 1, "ответ " +  users.users[2].name ,"сообщениеB")
        chats.writeNote(userId = users.users[0].id, 1, "ответ " +  users.users[0].name ,"сообщениеB")
        chats.writeNote(userId = users.users[3].id, 1, "ответ " +  users.users[3].name ,"сообщениеB")



        chats.writeNote(userId = users.users[1].id, 2, "заголовок " +  users.users[1].name ,"newMessageC")
        chats.writeNote(userId = users.users[2].id, 2, "ответ " +  users.users[2].name ,"сообщениеC")
        chats.writeNote(userId = users.users[0].id, 2, "ответ " +  users.users[0].name ,"сообщениеC")
        chats.writeNote(userId = users.users[3].id, 2, "ответ " +  users.users[3].name ,"сообщениеC")

        println("Чаты с чужими сообщениями где участвует " + users.users[1].name )
        users.getChats(users.users[1]).forEach { println(it)}

        println("Записи в чатах" )
        chats.allChats().forEach { chat: Chat ->
            println("\n---------" + chat.title + "-----------")
            chat.notes.notes.forEach {
                println("\nот " + users.users[it.ownerId].name)
                println(it.title)
                println(it.text)
            }
        }

        // Миша прочел чат "cinema" запись от Васи
        users.readNote(users.users[0].id, chats.chats[0].id, chats.chats[0].notes.notes[1].id)
        println("у " + users.users[0].name + " непрочитано чатов " + users.getUnreadChatsCount(users.users[0]))
        println("у " + users.users[3].name + " непрочитано чатов " + users.getUnreadChatsCount(users.users[3]))

        chats.deleteChat(1)
        println("===================================================")
        chats.allChats().forEach { chat: Chat ->
            println("---------" + chat.title + "-----------")
        }

        println("сообщения из чата ${chats.chats[1].title}")

        chats.chats[0].notes.notes.forEach{println(it.id.toString() + "\n" +  it.title + "\n" + it.text + "\n  ---------")}
        println(users.chatNotes(users.users[0], 0))
        println("===========")
        println(users.chatNotes(users.users[0], 0))
    }

