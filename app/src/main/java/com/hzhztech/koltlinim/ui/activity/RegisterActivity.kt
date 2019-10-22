package com.hzhztech.koltlinim.ui.activity

import android.view.KeyEvent
import android.widget.TextView
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.contract.RegisterContract
import com.hzhztech.koltlinim.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity :BaseActivity(),RegisterContract.View {

    val presenter = RegisterPresenter(this)

    override fun init() {
        super.init()
        register.setOnClickListener { register() }
        confirmPassword.setOnEditorActionListener { p0, p1, p2 ->
            register()
            true
        }
    }

    private fun register():Unit {
        hideSoftKeyboard()
        val userNameString = userName.text.trim().toString()
        val passwrodString = password.text.trim().toString()
        val confirmPasswordString = confirmPassword.text.trim().toString()
        presenter.register(userNameString,passwrodString,confirmPasswordString)
    }


    override fun onUserNameError() {
        userName.error = getString(R.string.user_name_error)
    }

    override fun onPasswordError() {
        password.error = getString(R.string.password_error)
    }

    override fun onConfirmPasswordError() {
        confirmPassword.error = getString(R.string.confirm_password_error)
    }

    override fun onStartRegister() {
       showProgress(getString(R.string.registering))
    }

    override fun onRegisterSuccess() {
        dismissProgress()
        finish()

    }

    override fun onRegisterFailed() {
        dismissProgress()
        toast(R.string.register_failed)
    }

    override fun getLayoutResId(): Int = R.layout.activity_register

}