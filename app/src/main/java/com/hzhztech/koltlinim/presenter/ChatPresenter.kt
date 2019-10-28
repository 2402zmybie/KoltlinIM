package com.hzhztech.koltlinim.presenter

import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hzhztech.koltlinim.adapter.EMCallBackAdapter
import com.hzhztech.koltlinim.contract.ChatContract
import org.jetbrains.anko.doAsync

class ChatPresenter(val view:ChatContract.View) :ChatContract.Presenter{

    companion object {
        val PAGE_SIZE = 10
    }

    val messages = mutableListOf<EMMessage>()

    override fun sendMessage(contact: String, message: String) {
        //创建一条消息
        val emMessage = EMMessage.createTxtSendMessage(message, contact)
        //消息状态的回调
        emMessage.setMessageStatusCallback(object : EMCallBackAdapter() {
            override fun onSuccess() {
                super.onSuccess()
                uiThread { view.onSendMessageSuccess() }
            }

            override fun onError(p0: Int, p1: String?) {
                super.onError(p0, p1)
                uiThread { view.onSendMessageFailed() }
            }
        })
        messages.add(emMessage)
        view.onStartSendMessage()
        EMClient.getInstance().chatManager().sendMessage(emMessage)
    }


    override fun addMessage(username: String, p0: MutableList<EMMessage>?) {
        p0?.let { messages.addAll(it) }
        //更新消息为已读消息
        //获取跟联系人的会话,然后标记会话里面的消息全部已读
        val conversation = EMClient.getInstance().chatManager().getConversation(username)
        conversation.markAllMessagesAsRead()
    }


    override fun loadMessages(username: String) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(username)
            messages.addAll(conversation.allMessages)
            uiThread {
                view.onMessageLoaded()
            }
        }
    }

    override fun loadMoreMessages(username: String) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(username)
            val startMsgId = messages[0].msgId
            val loadMoreMsgFromDB = conversation.loadMoreMsgFromDB(startMsgId, PAGE_SIZE)
            messages.addAll(0,loadMoreMsgFromDB)
            uiThread {
                view.onMoreMessageLoaded(loadMoreMsgFromDB.size)
            }
        }

    }


}