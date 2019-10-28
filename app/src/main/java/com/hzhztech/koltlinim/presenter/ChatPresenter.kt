package com.hzhztech.koltlinim.presenter

import com.hzhztech.koltlinim.contract.ChatContract

class ChatPresenter(val view:ChatContract.View) :ChatContract.Presenter{

    override fun sendMessage(contact: String, message: String) {

    }

}