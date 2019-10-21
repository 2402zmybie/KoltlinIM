package com.hzhztech.koltlinim.ui.activity

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

abstract class BaseActivity:AppCompatActivity() {

    val progressDialog by lazy {
        ProgressDialog(this)
    }

    val inputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        init()
    }

    open fun init() {
        //初始化一些公共的功能, 比如摇一摇, 子类也可以复写该方法, (加上open),以完成自己的初始化
    }

    //子类实现 返回布局资源的id
    abstract fun getLayoutResId(): Int

    fun showProgress(message: String) {
        progressDialog.setMessage(message)
        progressDialog.show()
    }
    fun dismissProgress() {
        progressDialog.dismiss()
    }

    fun hideSoftKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
    }
}