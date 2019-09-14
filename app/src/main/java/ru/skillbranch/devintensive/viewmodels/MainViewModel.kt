 package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.repositories.ChatRepository

class MainViewModel: ViewModel() {
    private val query = mutableLiveData("")
    private val chatRepository = ChatRepository
    private val chatItems = Transformations.map(chatRepository.loadChats()){chats ->
        return@map chats.filter { !it.isArchived }
            .map { it.toChatItem() }
            .sortedBy { it.id.toInt() }
    }

    fun getChatData(): LiveData<List<ChatItem>> {
        val result = MediatorLiveData<List<ChatItem>>()

        val filterF = {
            val queryStr = query.value!!
            val chats = chatItems.value!!.toMutableList()

            val chatArchiveItem = getChatArchiveItem()
            if (chatArchiveItem != null){
                chats.add(0, chatArchiveItem)
            }

            result.value = if (queryStr.isEmpty()) chats
            else chats.filter { it.title.contains(queryStr, true) }
        }

        result.addSource(chatItems){ filterF.invoke() }
        result.addSource(query){ filterF.invoke() }

        return result
    }

    private fun getChatArchiveItem(): ChatItem? {
//        val archivedChats = chatRepository.loadChats().value!!
//            .filter { it.isArchived }
//            .map { it.toChatItem() }
//
//        val chatArchiveItem = archivedChats.maxBy { it.id.toInt() }
//
//        return if (chatArchiveItem == null){
//            null
//        } else{
//            ChatItem(
//                chatArchiveItem.id,
//                chatArchiveItem.avatar,
//                chatArchiveItem.initials,
//                chatArchiveItem.title,
//                chatArchiveItem.shortDescription,
//                archivedChats.sumBy { it.messageCount },
//                chatArchiveItem.lastMessageDate,
//                chatArchiveItem.isOnline,
//                ChatType.ARCHIVE,
//                chatArchiveItem.author
//            )
//        }
        val archivedChats = chatRepository.loadChats().value!!.filter { it.isArchived }
        val lastChatArchive = archivedChats.maxBy { it.id.toInt() }

        return if (lastChatArchive == null){
            null
        } else {
            ChatItem(
                lastChatArchive.id,
                null,
                "",
                "",
                lastChatArchive.lastMessageShort().first,
                archivedChats.sumBy { it.unreadableMessageCount() },
                lastChatArchive.lastMessageDate()?.shortFormat(),
                false,
                ChatType.ARCHIVE,
                lastChatArchive.lastMessageShort().second
            )
        }
    }

    fun addToArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = true))
    }

    fun restoreFromArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }

    fun handleSearchQuery(text: String) {
        query.value = text
    }
}