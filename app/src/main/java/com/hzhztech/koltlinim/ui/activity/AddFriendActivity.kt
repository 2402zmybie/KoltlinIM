package com.hzhztech.koltlinim.ui.activity

import com.hzhztech.koltlinim.R
import kotlinx.android.synthetic.main.header.*

class AddFriendActivity :BaseActivity() {
    override fun getLayoutResId(): Int  = R.layout.activity_add_friend

    override fun init() {
        headerTitle.text = getString(R.string.add_friend)
    }

}