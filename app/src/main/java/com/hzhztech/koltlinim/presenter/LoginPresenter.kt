package com.hzhztech.koltlinim.presenter

import com.blankj.utilcode.util.LogUtils
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hzhztech.koltlinim.adapter.EMCallBackAdapter
import com.hzhztech.koltlinim.contract.LoginContract
import com.hzhztech.koltlinim.extentions.isValidPassword
import com.hzhztech.koltlinim.extentions.isValidUserName
import kotlin.math.log

class LoginPresenter(val view: LoginContract.View):LoginContract.LoginPresenter {

    override fun login(userName: String, password: String) {
        if(userName.isValidUserName()) {
            //用户名合法,继续校验密码
            if(password.isValidPassword()) {
                //通知view层开始登录
                view.onStartLogin()
                //登录环信
                loginEaseMob(userName,password)
            }else view.onPasswordError()
        }else view.onUserNameError()
    }

    private fun loginEaseMob(userName: String, password: String) {
        EMClient.getInstance().login(userName,password,object : EMCallBackAdapter() {
            //在子线程回调
            override fun onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                LogUtils.e("登录聊天服务器成功")
                //在主线程通知View层
                uiThread { view.onLoggedInSuccess() }
            }

            override fun onError(p0: Int, p1: String?) {
                super.onError(p0, p1)
                uiThread { view.onLOggedInFailed() }
            }
        })
    }
}