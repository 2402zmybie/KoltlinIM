package com.hzhztech.koltlinim.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.adapter.AddFriendListAdapter
import com.hzhztech.koltlinim.contract.AddFriendContract
import com.hzhztech.koltlinim.presenter.AddFriendPresenter
import kotlinx.android.synthetic.main.activity_add_friend.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.toast

class AddFriendActivity :BaseActivity() ,AddFriendContract.View{

    val presenter = AddFriendPresenter(this)

    override fun onSearchSuccess() {
        dismissProgress()
        toast(R.string.search_success)
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun onSearchFailed() {
        dismissProgress()
        toast(R.string.search_failed)
    }

    override fun getLayoutResId(): Int  = R.layout.activity_add_friend

    override fun init() {
        headerTitle.text = getString(R.string.add_friend)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = AddFriendListAdapter(context,presenter.addFriendItems)
        }

        search.setOnClickListener { search() }
        userName.setOnEditorActionListener { p0, p1, p2 ->
            search()
            true   //消费Edittext中键盘搜索事件
        }
    }

    private fun search() {
        hideSoftKeyboard()
        showProgress(getString(R.string.searching))
        var key = userName.text.trim().toString()
        presenter.search(key)
    }

}