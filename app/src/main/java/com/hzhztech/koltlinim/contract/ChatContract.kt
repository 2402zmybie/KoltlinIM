package com.hzhztech.koltlinim.contract

import com.hyphenate.chat.EMMessage

interface ChatContract {

    interface Presenter:BasePresenter {
        fun sendMessage(contact: String, message: String)
        fun addMessage(username: String, p0: MutableList<EMMessage>?)
    }

    interface View {
        fun onStartSendMessage()
        fun onSendMessageSuccess()
        fun onSendMessageFailed()
    }
}