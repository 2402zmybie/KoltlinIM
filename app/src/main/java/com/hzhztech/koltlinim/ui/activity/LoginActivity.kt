package com.hzhztech.koltlinim.ui.activity

import android.view.KeyEvent
import android.widget.TextView
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.contract.LoginContract
import com.hzhztech.koltlinim.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity: BaseActivity(),LoginContract.View {

    val presenter = LoginPresenter(this)

    override fun init() {
        super.init()
        //1 登录按钮登录
        login.setOnClickListener { userLogin() }
        //密码编辑框 点击软键盘Enter也触发登录
        password.setOnEditorActionListener { p0, p1, p2 ->
            userLogin()
            true
        }
    }

    private fun userLogin() {
        var userNameString = userName.text.toString().trim()
        var passwordString = password.text.toString().trim()
        presenter.login(userNameString,passwordString)
    }

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