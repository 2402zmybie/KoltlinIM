package com.hzhztech.koltlinim.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hzhztech.koltlinim.data.ContactListItem
import com.hzhztech.koltlinim.widget.ContractListItemView

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
    }

    class ContractListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}