package com.hzhztech.koltlinim.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hzhztech.koltlinim.R

class ReceiveMessageItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    init {
        View.inflate(context, R.layout.view_receive_message_item,this)
    }

}