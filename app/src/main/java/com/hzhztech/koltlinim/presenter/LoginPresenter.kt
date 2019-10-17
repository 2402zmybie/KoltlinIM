package com.hzhztech.koltlinim.presenter

import com.hzhztech.koltlinim.contract.LoginContract
import com.hzhztech.koltlinim.extentions.isValidPassword
import com.hzhztech.koltlinim.extentions.isValidUserName

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

    }
}