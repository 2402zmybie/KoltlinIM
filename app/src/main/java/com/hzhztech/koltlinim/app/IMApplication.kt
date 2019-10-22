package com.hzhztech.koltlinim.app

import android.app.Application
import cn.bmob.v3.Bmob
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.hzhztech.koltlinim.BuildConfig

class IMApplication:Application() {


    override fun onCreate() {
        super.onCreate()

        //初始化
        EMClient.getInstance().init(applicationContext, EMOptions());
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG);
        //Bmob
        Bmob.initialize(this, "5fc797898ece21e9b0aaa6b8ee4056c3");
    }
}