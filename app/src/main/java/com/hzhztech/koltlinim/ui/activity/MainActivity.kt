package com.hzhztech.koltlinim.ui.activity

import com.hyphenate.EMConnectionListener
import com.hyphenate.EMContactListener
import com.hyphenate.EMError
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.adapter.EMMessageListenerAdapter
import com.hzhztech.koltlinim.factory.FragmentFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity:BaseActivity() {

    val messageListener = object : EMMessageListenerAdapter() {
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            runOnUiThread { updateBottomUnreadCount() }
        }
    }

    override fun getLayoutResId(): Int  = R.layout.activity_main

    override fun init() {
        super.init()
        bottomBar.setOnTabSelectListener {tabId ->
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction.replace(R.id.fragment_frame, FragmentFactory.instance.getFragment(tabId)!!)
            beginTransaction.commit()
        }

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
        EMClient.getInstance().addConnectionListener(object :EMConnectionListener{
            override fun onConnected() {

            }

            override fun onDisconnected(p0: Int) {
                if(p0 == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    //发生多设备登录 则跳转到登录界面
                    startActivity<LoginActivity>()
                    finish()
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        updateBottomUnreadCount()
    }

    private fun updateBottomUnreadCount() {
        //初始化bottombar未读消息的个数
        val tab = bottomBar.getTabWithId(R.id.tab_conversation)
        tab.setBadgeCount(EMClient.getInstance().chatManager().unreadMessageCount)
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }
}