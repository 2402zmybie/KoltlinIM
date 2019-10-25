package com.hzhztech.koltlinim.data.db

import com.hzhztech.koltlinim.extentions.toVarargArray
import org.jetbrains.anko.db.insert

class IMDatabase  {

    companion object {
        val databaseHelper = DatabaseHelper()
        val instance = IMDatabase()
    }

    fun saveContact(contact: Contact) {
        databaseHelper.use {
            //SQLiteDatabase的扩展方法
            insert(ContactTable.NAME, *contact.map.toVarargArray())
        }
    }
}