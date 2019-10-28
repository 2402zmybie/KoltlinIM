package com.hzhztech.koltlinim.widget

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import com.hzhztech.koltlinim.R
import kotlinx.android.synthetic.main.view_send_message_item.view.*
import java.util.*

class SendMessageItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.view_send_message_item,this)
    }

    fun bindView(emMessage: EMMessage) {
        updateTimeStamp(emMessage)
        updateMessage(emMessage)
        updateProgress(emMessage)
    }

    private fun updateProgress(emMessage: EMMessage) {
        emMessage.status().let {
            when (it) {
                EMMessage.Status.INPROGRESS -> {
                    //菊花转起来
                    sendMessageProgress.visibility = View.VISIBLE
                    sendMessageProgress.setImageResource(R.drawable.send_message_progress)
                    val animationDrawable = sendMessageProgress.drawable as AnimationDrawable
                    animationDrawable.start()
                }
                EMMessage.Status.SUCCESS -> {
                    sendMessageProgress.visibility = View.GONE
                }
                EMMessage.Status.FAIL -> {
                    sendMessageProgress.visibility = View.VISIBLE
                    sendMessageProgress.setImageResource(R.mipmap.msg_error)
                }
                else -> {
                    sendMessageProgress.visibility = View.VISIBLE
                    sendMessageProgress.setImageResource(R.mipmap.msg_error)
                }
            }
        }
    }

    private fun updateMessage(emMessage: EMMessage) {
        if(emMessage.type == EMMessage.Type.TXT) {
            //文本消息
            sendMessage.text = (emMessage.body as EMTextMessageBody).message
        }else {
            sendMessage.text = context.getString(R.string.no_text_message)
        }
    }

    private fun updateTimeStamp(emMessage: EMMessage) {
        timestamp.text = DateUtils.getTimestampString(Date(emMessage.msgTime))
    }

}