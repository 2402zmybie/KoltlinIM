package com.hzhztech.koltlinim.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.contract.ChatContract
import com.hzhztech.koltlinim.presenter.ChatPresenter
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.toast

class ChatActixvity :BaseActivity(),ChatContract.View {

    lateinit var username:String
    val presenter = ChatPresenter(this)

    override fun getLayoutResId(): Int  = R.layout.activity_chat

    override fun init() {
        super.init()
        initHeader()
        initEditText()
        send.setOnClickListener { send() }
    }

    private fun send() {
        hideSoftKeyboard()
        var message = edit.text.toString().trim()
        presenter.sendMessage(username,message)
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

        edit.setOnEditorActionListener { p0, p1, p2 ->
            send()
            true
        }
    }

    private fun initHeader() {
        back.visibility = View.VISIBLE
        back.setOnClickListener { finish() }
        //获取聊天的用户名
        username = intent.getStringExtra("username")
        val titleString = String.format(getString(R.string.chat_title),username)
        headerTitle.text = titleString
    }


    override fun onStartSendMessage() {
        //通知RecyclerView刷新数据
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun onSendMessageSuccess() {
        recyclerView.adapter!!.notifyDataSetChanged()
        toast(getString(R.string.send_message_success))
        edit.text.clear()
    }

    override fun onSendMessageFailed() {
        toast(getString(R.string.send_message_failed))
        recyclerView.adapter!!.notifyDataSetChanged()   //消息状态图片变为出错的
    }

}