package com.hzhztech.koltlinim.presenter

import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.blankj.utilcode.util.LogUtils
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.a.e
import com.hyphenate.exceptions.HyphenateException
import com.hzhztech.koltlinim.contract.RegisterContract
import com.hzhztech.koltlinim.extentions.isValidPassword
import com.hzhztech.koltlinim.extentions.isValidUserName
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class RegisterPresenter(val view: RegisterContract.View):RegisterContract.Presenter {
    override fun register(userName: String, password: String, confirmPassword: String) {
        if(userName.isValidUserName()) {
            //用户名合法, 检查密码
            if(password.isValidPassword()) {
                //检查确认密码
                if(password.equals(confirmPassword)) {
                    //密码和确认密码相同
                    view.onStartRegister()
                    //开始注册, Model层要做的事情
                    registerBmob(userName,password)
                }else view.onConfirmPasswordError()
            }else view.onPasswordError()
        }else view.onUserNameError()
    }

    private fun registerBmob(userName: String, password: String) {
        val bu = BmobUser()
        bu.username = userName
        bu.setPassword(password)
        //注意: 不能用save方法进行注册
        bu.signUp(object : SaveListener<BmobUser>() {
            override fun done(p0: BmobUser?, p1: BmobException?) {
                if(p1 == null) {
                    //注册成功
                    //注册环信
                    registerEaseMob(userName,password)
                }else {
                    //注册失败
                    LogUtils.e(p1.toString())
                    if(p1.errorCode == 202) view.onUserExist()
                    else view.onRegisterFailed()
                }
            }

        })
    }

    private fun registerEaseMob(userName: String, password: String) {
        doAsync {
            //环信 注册失败会抛出 HyphenateException
            try {
                EMClient.getInstance().createAccount(userName,password)  //同步方法
                //注册成功
                //切换主线程
                uiThread {
                    view.onRegisterSuccess()
                }
            }catch (e: HyphenateException) {
                //注册失败
                uiThread {
                    view.onRegisterFailed()
                }
            }

        }
    }
}