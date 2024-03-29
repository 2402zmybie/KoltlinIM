package com.hzhztech.koltlinim.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.adapter.EMMessageListenerAdapter
import com.hzhztech.koltlinim.adapter.MessageListAdapter
import com.hzhztech.koltlinim.contract.ChatContract
import com.hzhztech.koltlinim.presenter.ChatPresenter
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.toast

class ChatActivity :BaseActivity(),ChatContract.View {


    lateinit var username:String
    val presenter = ChatPresenter(this)

    val messageListener = object : EMMessageListenerAdapter() {
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            presenter.addMessage(username,p0)
            runOnUiThread {
                recyclerView.adapter!!.notifyDataSetChanged()
                scrollToBottom()
            }
        }
    }

    override fun getLayoutResId(): Int  = R.layout.activity_chat

    override fun init() {
        super.init()
        initHeader()
        initEditText()
        initRecyclerView()
        EMClient.getInstance().chatManager().addMessageListener(messageListener)
        send.setOnClickListener { send() }
        presenter.loadMessages(username)
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = MessageListAdapter(context,presenter.messages)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    //当RecyclerView是一个空闲状态
                    //检查是否滑到顶部,要加载更多数据
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //如果第一个可见条目是0  为滑动到顶部
                        val linearLayoutManager = layoutManager as LinearLayoutManager
                        if(linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                            //加载更多数据
                            presenter.loadMoreMessages(username)
                        }
                    }
                }
            })
        }
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
        scrollToBottom()
    }

    private fun scrollToBottom() {
        recyclerView.scrollToPosition(presenter.messages.size -1)
    }

    override fun onSendMessageFailed() {
        toast(getString(R.string.send_message_failed))
        recyclerView.adapter!!.notifyDataSetChanged()   //消息状态图片变为出错的
    }

    override fun onMessageLoaded() {
        scrollToBottom()
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun onMoreMessageLoaded(size: Int) {
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scrollToPosition(size)
    }



    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }

}