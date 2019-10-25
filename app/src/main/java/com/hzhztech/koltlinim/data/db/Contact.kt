package com.hzhztech.koltlinim.data.db

data class Contact (val map: MutableMap<String, Any?>) {
    val _id by map
    val name by map
}