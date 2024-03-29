package com.hzhztech.koltlinim.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hyphenate.chat.EMMessage
import com.hyphenate.util.DateUtils
import com.hzhztech.koltlinim.widget.ReceiveMessageItemView
import com.hzhztech.koltlinim.widget.SendMessageItemView

class MessageListAdapter(val context:Context, val messages:List<EMMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val ITEM_TYPE_SEND_MESSAGE = 0
        val ITEM_TYPE_RECEIVE_MESSAGE = 1
    }

    override fun getItemViewType(position: Int): Int {
        if(messages[position].direct() == EMMessage.Direct.SEND) {
            //条目类型为发送消息
            return ITEM_TYPE_SEND_MESSAGE
        }else {
            return ITEM_TYPE_RECEIVE_MESSAGE
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == ITEM_TYPE_SEND_MESSAGE) {
            return SendMessageViewHolder(SendMessageItemView(context))
        }else {
            return ReceiveMessageViewHolder(ReceiveMessageItemView(context))
        }
    }

    override fun getItemCount(): Int  = messages.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, p1: Int) {
        val showTimeStamp = isShowTimeStamp(p1)
        if(getItemViewType(p1) == ITEM_TYPE_SEND_MESSAGE) {
            val sendMessageItemView = viewHolder.itemView as SendMessageItemView
            sendMessageItemView.bindView(messages[p1], showTimeStamp)
        }else {
            val receiveMessageItemView = viewHolder.itemView as ReceiveMessageItemView
            receiveMessageItemView.bindView(messages[p1], showTimeStamp)
        }
    }

    private fun isShowTimeStamp(position: Int): Boolean {
        //如果是第一条消息或者和前一条消息间隔的时间比较长 显示时间戳
        var showTimeStamp = true
        if(position > 0) {
            showTimeStamp = !DateUtils.isCloseEnough(messages[position].msgTime, messages[position - 1].msgTime)
        }
        return showTimeStamp
    }

    class SendMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class ReceiveMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}