package com.hzhztech.koltlinim.adapter

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.data.ContactListItem
import com.hzhztech.koltlinim.ui.activity.ChatActivity
import com.hzhztech.koltlinim.widget.ContractListItemView
import org.jetbrains.anko.startActivity

class ContractListAdapter(val context: Context, val contactListItems: MutableList<ContactListItem>) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return ContractListItemViewHolder(ContractListItemView(context))
    }

    override fun getItemCount(): Int {
        return contactListItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contractListItemView = holder.itemView as ContractListItemView
        contractListItemView.bindView(contactListItems[position])
        //点击联系人跳转到聊天界面
        val userName = contactListItems.get(position).userName
        contractListItemView.setOnClickListener { context.startActivity<ChatActivity>("username" to userName) }
        contractListItemView.setOnLongClickListener {
            val message = String.format(context.getString(R.string.delete_friend_message), userName)
            AlertDialog.Builder(context).setTitle(R.string.delete_friend_title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel,null)
                .setPositiveButton(R.string.confirm, DialogInterface.OnClickListener { dialogInterface, i ->
                    deleteFriend(userName)
                })
                .show()
            true

        }
    }

    private fun deleteFriend(userName: String) {

    }

    class ContractListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}