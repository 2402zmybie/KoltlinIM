package com.hzhztech.koltlinim.contract

import android.os.Handler
import android.os.Looper

interface BasePresenter {

    companion object {
        val handler by lazy {
            Handler(Looper.getMainLooper())
        }
    }

    fun uiThread(f: () -> Unit) {
        handler.post {
            f.invoke()
        }
    }

}