package com.hzhztech.koltlinim.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
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
        //隐藏软键盘
        hideSoftKeyboard()
        if(hasWriteExternalStoragePermission()) {
            var userNameString = userName.text.toString().trim()
            var passwordString = password.text.toString().trim()
            presenter.login(userNameString,passwordString)
        }else applyWriteExternalStoragePermission()

    }

    //申请写入的权限(会弹一个对话框,让用户选择, 后有一个回调)
    private fun applyWriteExternalStoragePermission() {
        var permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this,permissions,0)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //用户同意权限,开始登录
            userLogin()
        }else toast(R.string.permission_denied)
    }

    //检查是否有写磁盘的操作  在android 6.0上面
    private fun hasWriteExternalStoragePermission(): Boolean {
        val result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
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