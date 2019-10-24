package com.hzhztech.koltlinim.presenter

import com.blankj.utilcode.util.LogUtils
import com.hzhztech.koltlinim.contract.ContactContract
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.hzhztech.koltlinim.data.ContactListItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class ContactPresenter(val view:ContactContract.View) :ContactContract.Presenter {
    //mutableListOf  是可变的List集合
    val contactListItems = mutableListOf<ContactListItem>()

    override fun loadContracts() {
        doAsync {
            //再次加载数据 清空集合
            contactListItems.clear()
            try {
                //由于环信是同步的方法, 所以放在doAsync中, 然后用anko库的uiThread 通知View层
                val usernames = EMClient.getInstance().contactManager().allContactsFromServer
                //根据首字符排序
                usernames.sortBy { it[0] }
                usernames.forEachIndexed { index, s ->
                    //判断是否显示首字符
                    val showFirstLetter = index == 0 || s[0] != usernames[index - 1][0]
                    val contactListItem = ContactListItem(s,s[0].toUpperCase(),showFirstLetter)
                    contactListItems.add(contactListItem)
                }
                LogUtils.e(EMClient.getInstance().currentUser + "好友数量------" + usernames.size)
                uiThread {
                    view.onLoadContractSuccess()
                }
            }catch (e:HyphenateException) {
                uiThread {
                    view.onLoadContractsFailed()
                }
            }

        }
    }

}