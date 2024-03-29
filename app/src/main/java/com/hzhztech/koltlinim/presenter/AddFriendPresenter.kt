package com.hzhztech.koltlinim.presenter

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.hyphenate.chat.EMClient
import com.hzhztech.koltlinim.contract.AddFriendContract
import com.hzhztech.koltlinim.data.AddFriendItem
import com.hzhztech.koltlinim.data.db.IMDatabase
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
                        //得到数据库中好友的集合
                        val allContacts = IMDatabase.instance.getAllContacts()
                        //查询成功, 处理数据
                        doAsync {
                            p0?.forEach {
                                //比对是否已经添加过好友
                                var isAdded = false
                                for(contact in allContacts) {
                                    if(contact.name == it.username) {
                                        isAdded = true
                                    }
                                }
                                val addFriendItem = AddFriendItem(it.username,it.createdAt,isAdded)
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