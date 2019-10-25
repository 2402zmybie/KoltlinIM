package com.hzhztech.koltlinim.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hyphenate.chat.EMClient
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.adapter.EMCallBackAdapter
import com.hzhztech.koltlinim.data.AddFriendItem
import kotlinx.android.synthetic.main.view_add_friend_item.view.*
import kotlinx.android.synthetic.main.view_contact_item.view.*
import kotlinx.android.synthetic.main.view_contact_item.view.userName
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

class AddFriendListItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {


    init {
        View.inflate(context, R.layout.view_add_friend_item,this)
    }


    fun bindView(addFriendItem: AddFriendItem) {
        if(addFriendItem.isAdded) {
            add.isEnabled = false
            add.text = context.getString(R.string.already_added)
        }else {
            add.isEnabled = true
            add.text = context.getString(R.string.add)
        }
        add.setOnClickListener {
            addFriend(addFriendItem.userName)
        }
        userName.text = addFriendItem.userName
        timestamp.text = addFriendItem.timestamp
    }

    private fun addFriend(userName: String) {
        EMClient.getInstance().contactManager().aysncAddContact(userName,null, object : EMCallBackAdapter() {
            override fun onSuccess() {
                context.runOnUiThread { toast(R.string.send_add_friend_success) }
            }

            override fun onError(p0: Int, p1: String?) {
                context.runOnUiThread { toast(R.string.send_add_friend_failed) }
            }
        })

    }

}