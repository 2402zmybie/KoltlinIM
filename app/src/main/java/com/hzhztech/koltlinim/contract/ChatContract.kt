package com.hzhztech.koltlinim.contract

interface ChatContract {

    interface Presenter:BasePresenter {
        fun sendMessage(contact: String, message: String)
    }

    interface View {
        fun onStartSendMessage()
        fun onSendMessageSuccess()
        fun onSendMessageFailed()
    }
}