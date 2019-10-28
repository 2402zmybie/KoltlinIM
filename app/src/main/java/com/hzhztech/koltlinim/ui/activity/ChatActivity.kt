package com.hzhztech.koltlinim.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.hzhztech.koltlinim.R
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.header.*

class ChatActivity :BaseActivity() {
    override fun getLayoutResId(): Int  = R.layout.activity_chat

    override fun init() {
        super.init()
        initHeader()
        initEditText()
    }

    private fun initEditText() {
        edit.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                //如果用户输入的文本长度大于0, 发送按钮Enable
                send.isEnabled = !p0.isNullOrEmpty()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    private fun initHeader() {
        back.visibility = View.VISIBLE
        back.setOnClickListener { finish() }
        //获取聊天的用户名
        val username = intent.getStringExtra("username")
        val titleString = String.format(getString(R.string.chat_title),username)
        headerTitle.text = titleString
    }

}