package com.hzhztech.koltlinim.presenter

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.hyphenate.chat.EMClient
import com.hzhztech.koltlinim.contract.AddFriendContract
import com.hzhztech.koltlinim.data.AddFriendItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AddFriendPresenter(val view :AddFriendContract.View) : AddFriendContract.Presenter {

    val addFriendItems = mutableListOf<AddFriendItem>()

    override fun search(key: String) {
        val query = BmobQuery<BmobUser>()
        query.addWhereContains("username",key)
            .addWhereNotEqualTo("username",EMClient.getInstance().currentUser)
            .findObjects(object :FindListener<BmobUser>() {
                override fun done(p0: MutableList<BmobUser>?, p1: BmobException?) {
                    //主线程
                    if(p1 == null) {
                        //查询成功, 处理数据
                        doAsync {
                            p0?.forEach {
                                val addFriendItem = AddFriendItem(it.username,it.createdAt)
                                addFriendItems.add(addFriendItem)
                            }
                            uiThread {
                                view.onSearchSuccess()
                            }
                        }
                    }
                    else view.onSearchFailed()
                }

            })
    }

}