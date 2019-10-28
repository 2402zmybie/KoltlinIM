package com.hzhztech.koltlinim.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import com.hzhztech.koltlinim.R
import kotlinx.android.synthetic.main.view_receive_message_item.view.*
import java.util.*

class ReceiveMessageItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {


    init {
        View.inflate(context, R.layout.view_receive_message_item,this)
    }

    fun bindView(emMessage: EMMessage) {
        updateMessage(emMessage)
        updateTimeStamp(emMessage)
    }


    private fun updateMessage(emMessage: EMMessage) {
        if(emMessage.type == EMMessage.Type.TXT) {
            //文本消息
            receiveMessage.text = (emMessage.body as EMTextMessageBody).message
        }else {
            receiveMessage.text = context.getString(R.string.no_text_message)
        }
    }

    private fun updateTimeStamp(emMessage: EMMessage) {
        timestamp.text = DateUtils.getTimestampString(Date(emMessage.msgTime))
    }

}