package com.hzhztech.koltlinim.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.adapter.ContractListAdapter
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.header.*

class ContactPersonFragment :BaseFragment() {
    override fun getLayoutResId(): Int  = R.layout.fragment_contacts

    override fun init() {
        super.init()
        headerTitle.text = getString(R.string.contact)
        add.visibility = View.VISIBLE

        swipeRefreshLayout.apply {
            setColorSchemeResources(R.color.colorPrimary)
            isRefreshing = true
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ContractListAdapter(context)
        }
    }
}