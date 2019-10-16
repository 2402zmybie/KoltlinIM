package com.hzhztech.koltlinim.presenter

import com.hyphenate.chat.EMClient
import com.hzhztech.koltlinim.contract.SplashContract

class SplashPresenter(val view: SplashContract.View):SplashContract.Presenter {
    override fun checkLoginStatus() {
        if(isLoggedIn()) view.onLoggedIn() else view.onNotLoggedIn()
    }

    //是否登录到环信的服务器(环信服务器连接 并且已经登陆过)
    private fun isLoggedIn(): Boolean  =
            EMClient.getInstance().isConnected && EMClient.getInstance().isLoggedInBefore
}