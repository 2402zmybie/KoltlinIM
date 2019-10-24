package com.hzhztech.koltlinim.presenter

import com.blankj.utilcode.util.LogUtils
import com.hzhztech.koltlinim.contract.ContactContract
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class ContactPresenter(val view:ContactContract.View) :ContactContract.Presenter {
    override fun loadContracts() {
        doAsync {
            try {
                //由于环信是同步的方法, 所以放在doAsync中, 然后用anko库的uiThread 通知View层
                val usernames = EMClient.getInstance().contactManager().allContactsFromServer
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