package com.hzhztech.koltlinim.factory

import android.support.v4.app.Fragment
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.ui.fragment.ContactPersonFragment
import com.hzhztech.koltlinim.ui.fragment.ConversationFragment
import com.hzhztech.koltlinim.ui.fragment.DynamicFragment

//单例模式, 私有构造, 伴生对象保存实例
class FragmentFactory private constructor() {

    val conversation by lazy {
        ConversationFragment()
    }
    val contract by lazy {
        ContactPersonFragment()
    }
    val dymanic by lazy {
        DynamicFragment()
    }

    companion object {
        val instance = FragmentFactory()
    }

    fun getFragment(tabId: Int) : Fragment? {
        when(tabId) {
            R.id.tab_conversation -> return conversation
            R.id.tab_contacts -> return contract
            R.id.tab_dynamic -> return dymanic
        }
        return null
    }
}