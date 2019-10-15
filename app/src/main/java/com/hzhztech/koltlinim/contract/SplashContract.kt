package com.hzhztech.koltlinim.contract

interface SplashContract {

    interface Presenter:BasePresenter {
        fun checkLoginStatus()  //检查登录状态
    }

    interface View {
        /**
         * 没有登录的ui处理
         */
        fun onNotLoggedIn()

        /**
         * 已经登录的ui处理
         */
        fun onLoggedIn()
    }
}