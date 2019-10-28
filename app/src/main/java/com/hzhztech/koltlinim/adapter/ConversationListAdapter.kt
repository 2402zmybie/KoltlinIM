package com.hzhztech.koltlinim.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hyphenate.chat.EMConversation
import com.hzhztech.koltlinim.ui.activity.ChatActivity
import com.hzhztech.koltlinim.widget.ConversationListItemView
import org.jetbrains.anko.startActivity

class ConversationListAdapter(
    val context: Context,
    val conversations: MutableList<EMConversation>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return ConversationListItemViewHolder(ConversationListItemView(context))
    }

    override fun getItemCount(): Int = conversations.size

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val conversationListItemView = p0.itemView as ConversationListItemView
        conversationListItemView.bindView(conversations[p1])
        conversationListItemView.setOnClickListener { context.startActivity<ChatActivity>("username" to conversations[p1].conversationId())}
    }

    class ConversationListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}