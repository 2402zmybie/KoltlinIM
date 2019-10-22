package com.hzhztech.koltlinim.presenter

import com.hzhztech.koltlinim.contract.RegisterContract
import com.hzhztech.koltlinim.extentions.isValidPassword
import com.hzhztech.koltlinim.extentions.isValidUserName

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

                }else view.onConfirmPasswordError()
            }else view.onPasswordError()
        }else view.onUserNameError()
    }
}