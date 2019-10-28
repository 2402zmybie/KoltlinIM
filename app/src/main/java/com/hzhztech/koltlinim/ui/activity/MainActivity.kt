package com.hzhztech.koltlinim.ui.activity

import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.adapter.EMMessageListenerAdapter
import com.hzhztech.koltlinim.factory.FragmentFactory
import kotlinx.android.synthetic.main.activity_main.*

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