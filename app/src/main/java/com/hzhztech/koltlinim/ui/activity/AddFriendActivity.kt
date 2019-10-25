package com.hzhztech.koltlinim.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.adapter.AddFriendListAdapter
import kotlinx.android.synthetic.main.activity_add_friend.*
import kotlinx.android.synthetic.main.header.*

class AddFriendActivity :BaseActivity() {
    override fun getLayoutResId(): Int  = R.layout.activity_add_friend

    override fun init() {
        headerTitle.text = getString(R.string.add_friend)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = AddFriendListAdapter(context)
        }
    }

}