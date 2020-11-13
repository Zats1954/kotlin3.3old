package ru.netology

class NoteService {
    var notes = mutableListOf<Note>()

    fun add(title: String, text: String): Int {
        val newId = if (notes.isEmpty()) {
            0
        } else {
            notes[notes.lastIndex].id + 1
        }
        val newNote = Note(id = newId, title = title, text = text)
        notes.add(newNote)
        return newId
    }

    fun get(noteIds: String = "",
            userId: Int = 0,
            offset: Int = 0,
            count: Int = 20,
            sort: Int = 0): List<Note>? {
        val idNotes: List<Int> = noteIds.split(",").map { it.toInt() }
        return if (idNotes.isNotEmpty()) {
            notes.filter { idNotes.contains(it.id) }
        } else {
            notes
        }
    }

    fun getById(noteIds: Int,
                ownerId: Int = 0,
                needWiki: Int = 0
    ): Note? {
        notes.firstOrNull { it.id == noteIds }
                .let {
                    if (it == null) {
                        println("getById: Ошибка 180 Сообщение $noteIds не найдено ")
                    }
                    return it
                }
    }


    fun edit(noteIds: Int,
             title: String,
             text: String,
             privacyView: String = "all",
             privacyComment: String = "all"
    ): Int {
        notes.find { it.id == noteIds }.let {
            if (it != null) {
                it.title = title
                it.text = text
                return 1
            }
        }
        throw NoteException(180, "edit: Сообщение $noteIds не найдено ")
    }

    fun delete(noteIds: Int): Int {
        for ((index, note) in notes.withIndex()) {
            if (note.id == noteIds) {
                notes.removeAt(index)
                return 1
            }
        }
        throw NoteException(180, "delete: Сообщение $noteIds не найдено ")
    }

    fun maxId():Int{var maxId = 0
                    notes.forEach(){if(it.id > maxId) maxId = it.id}
                    return maxId }
}

class NoteException(cod: Int?, message: String?) : Throwable() {
    val errorN: Any = if (cod != null) println("Error $cod") else {
    }
    val errorMessage: Any = if (message != null) println(message) else {
    }
}



