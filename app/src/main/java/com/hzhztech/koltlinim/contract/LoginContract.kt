package com.hzhztech.koltlinim.contract

interface LoginContract {

    interface LoginPresenter:BasePresenter {
        fun login(userName:String, password:String)
    }

    interface View {
        fun onUserNameError()
        fun onPasswordError()
        fun onStartLogin()
        fun onLoggedInSuccess()
        fun onLOggedInFailed()
    }
}