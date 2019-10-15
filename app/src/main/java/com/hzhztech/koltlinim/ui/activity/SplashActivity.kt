package com.hzhztech.koltlinim.ui.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.contract.SplashContract
import org.jetbrains.anko.startActivity

class SplashActivity: BaseActivity() ,SplashContract.View{

    companion object  {
        val DELAY = 2000L
    }

    val handler by lazy {
        Handler()
    }

    override fun getLayoutResId(): Int
        = R.layout.splash_activity

    //没有登录 延时2s跳转到登录界面
    override fun onNotLoggedIn() {
         handler.postDelayed({
             //anko库跳转到 LoginActivity
             startActivity<LoginActivity>()
             finish()
         },DELAY)
    }

    //已经登录 跳转到主页面
    override fun onLoggedIn() {
        startActivity<MainActivity>()
        finish()
    }

}