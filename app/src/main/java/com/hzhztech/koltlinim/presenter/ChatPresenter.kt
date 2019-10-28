package com.hzhztech.koltlinim.presenter

import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hzhztech.koltlinim.adapter.EMCallBackAdapter
import com.hzhztech.koltlinim.contract.ChatContract

class ChatPresenter(val view:ChatContract.View) :ChatContract.Presenter{

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

}