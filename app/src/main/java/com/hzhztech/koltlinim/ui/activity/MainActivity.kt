package com.hzhztech.koltlinim.ui.activity

import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.factory.FragmentFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity:BaseActivity() {
    override fun getLayoutResId(): Int  = R.layout.activity_main

    override fun init() {
        super.init()
        bottomBar.setOnTabSelectListener {tabId ->
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction.replace(R.id.fragment_frame, FragmentFactory.instance.getFragment(tabId)!!)
            beginTransaction.commit()
        }
    }
}