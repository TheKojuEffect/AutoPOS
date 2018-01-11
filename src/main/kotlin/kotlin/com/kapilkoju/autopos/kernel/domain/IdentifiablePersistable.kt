package com.kapilkoju.autopos.kernel.domain

import java.io.Serializable

interface IdentifiablePersistable<out ID : Serializable> : Serializable {

    fun getIdentity(): ID?

    fun isNew(): Boolean
}

