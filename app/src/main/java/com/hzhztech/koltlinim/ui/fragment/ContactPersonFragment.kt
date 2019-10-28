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
import com.hzhztech.koltlinim.ui.activity.AddFriendActivity
import com.hzhztech.koltlinim.widget.SlideBar
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class ContactPersonFragment :BaseFragment(),ContactContract.View {

    val presenter = ContactPresenter(this)

    override fun getLayoutResId(): Int  = R.layout.fragment_contacts

    var contactListener = object : EMContactListenerAdapter() {
        override fun onContactDeleted(p0: String?) {
            //好友被删除
            //重新获取联系人数据
            presenter.loadContracts()
        }

        override fun onContactAdded(p0: String?) {
            //新增好友
            //重新获取联系人数据
            presenter.loadContracts()
        }
    }

    override fun init() {
        super.init()

        initHeader()
        initSwipeRefreshLayout()
        initRecyclerView()
        EMClient.getInstance().contactManager().setContactListener(contactListener)
        initSlideBar()
        presenter.loadContracts()
    }

    private fun initSlideBar() {
        slideBar.onSectionChangeListener = object : SlideBar.OnSectionChangeListener {
            override fun onSectionChange(firstLetter: String) {
                section.visibility = View.VISIBLE
                section.text = firstLetter
                recyclerView.smoothScrollToPosition(getPosition(firstLetter))
            }

            override fun onSlideFinish() {
                section.visibility = View.GONE
            }

        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ContractListAdapter(context, presenter.contactListItems)
        }
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.apply {
            setColorSchemeResources(R.color.colorPrimary)
            isRefreshing = true
            setOnRefreshListener { presenter.loadContracts() }
        }
    }

    //抽取方法快捷键 ctrl + ale + M
    private fun initHeader() {
        headerTitle.text = getString(R.string.contact)
        add.visibility = View.VISIBLE
        add.setOnClickListener { startActivity<AddFriendActivity>() }
    }


    private fun getPosition(firstLetter: String): Int =
        presenter.contactListItems.binarySearch {
            contactListItem -> contactListItem.firstLetter.minus(firstLetter[0])
        }

    override fun onLoadContractSuccess() {
        if(swipeRefreshLayout != null) {
            swipeRefreshLayout.isRefreshing = false
        }
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun onLoadContractsFailed() {
        if(swipeRefreshLayout != null) {
            swipeRefreshLayout.isRefreshing = false
        }
        toast(R.string.load_contacts_failed)
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().contactManager().removeContactListener(contactListener)
    }
}