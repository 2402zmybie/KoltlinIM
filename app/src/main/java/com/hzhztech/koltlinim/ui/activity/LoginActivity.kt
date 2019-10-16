package com.hzhztech.koltlinim.ui.activity

import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.contract.LoginContract
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity: BaseActivity(),LoginContract.View {

    override fun onUserNameError() {
        userName.error = getString(R.string.user_name_error)
    }

    override fun onPasswordError() {
        password.error = getString(R.string.password_error)
    }

    override fun onStartLogin() {
        //弹出进度条
        showProgress(getString(R.string.logging))
    }

    override fun onLoggedInSuccess() {
        dismissProgress()
        //进入主界面
        startActivity<MainActivity>()
        finish()
    }

    override fun onLOggedInFailed() {
        dismissProgress()
        toast(getString(R.string.login_failed))
    }

    override fun getLayoutResId(): Int  = R.layout.activity_login
}