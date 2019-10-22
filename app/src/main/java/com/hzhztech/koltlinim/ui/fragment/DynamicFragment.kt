package com.hzhztech.koltlinim.ui.fragment

import com.hyphenate.chat.EMClient
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.adapter.EMCallBackAdapter
import com.hzhztech.koltlinim.ui.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_dynamic.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast

class DynamicFragment :BaseFragment() {
    override fun getLayoutResId(): Int  = R.layout.fragment_dynamic

    override fun init() {
        super.init()
        headerTitle.text = getString(R.string.dynamic)

        val logoutString = String.format(getString(R.string.logout),EMClient.getInstance().currentUser)
        logout.text = logoutString
        logout.setOnClickListener { logout() }
    }

    fun logout() {
        //异步执行
        EMClient.getInstance().logout(true, object : EMCallBackAdapter() {
            override fun onSuccess() {
                super.onSuccess()
                context!!.runOnUiThread {
                    startActivity<LoginActivity>()
                    activity!!.finish()
                    toast(R.string.logout_success)
                }

            }

            override fun onError(p0: Int, p1: String?) {
                super.onError(p0, p1)
                //切换到主线程
                context!!.runOnUiThread {
                    toast(R.string.logout_failed)
                }
            }
        })
    }

}