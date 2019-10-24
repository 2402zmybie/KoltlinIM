package com.hzhztech.koltlinim.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hyphenate.EMContactListener
import com.hyphenate.chat.EMClient
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.adapter.ContractListAdapter
import com.hzhztech.koltlinim.adapter.EMContactListenerAdapter
import com.hzhztech.koltlinim.contract.ContactContract
import com.hzhztech.koltlinim.presenter.ContactPresenter
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.support.v4.toast

class ContactPersonFragment :BaseFragment(),ContactContract.View {

    val presenter = ContactPresenter(this)

    override fun getLayoutResId(): Int  = R.layout.fragment_contacts

    override fun init() {
        super.init()
        headerTitle.text = getString(R.string.contact)
        add.visibility = View.VISIBLE

        swipeRefreshLayout.apply {
            setColorSchemeResources(R.color.colorPrimary)
            isRefreshing = true
            setOnRefreshListener { presenter.loadContracts() }
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ContractListAdapter(context,presenter.contactListItems)
        }

        EMClient.getInstance().contactManager().setContactListener(object : EMContactListenerAdapter() {
            override fun onContactDeleted(p0: String?) {
                //好友被删除
                //重新获取联系人数据
                presenter.loadContracts()
            }
        })

        presenter.loadContracts()
    }

    override fun onLoadContractSuccess() {
        swipeRefreshLayout.isRefreshing = false
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun onLoadContractsFailed() {
        swipeRefreshLayout.isRefreshing = false
        toast(R.string.load_contacts_failed)
    }
}