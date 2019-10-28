package com.hzhztech.koltlinim.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.adapter.ConversationListAdapter
import kotlinx.android.synthetic.main.fragment_contacts.recyclerView
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ConversationFragment :BaseFragment() {

    val conversations = mutableListOf<EMConversation>()

    override fun getLayoutResId(): Int  = R.layout.fragment_conversation

    override fun init() {
        super.init()
        headerTitle.text = getString(R.string.message)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ConversationListAdapter(context,conversations)
        }

        loadConversations()
    }

    private fun loadConversations() {
        doAsync {
            val allConversations = EMClient.getInstance().chatManager().allConversations
            conversations.addAll(allConversations.values)
            uiThread {
                recyclerView.adapter!!.notifyDataSetChanged()
            }
        }
    }

}