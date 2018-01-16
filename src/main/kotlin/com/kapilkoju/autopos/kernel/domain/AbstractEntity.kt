package com.kapilkoju.autopos.kernel.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.util.ClassUtils
import java.io.Serializable
import javax.persistence.MappedSuperclass
import javax.persistence.Transient

@MappedSuperclass
abstract class AbstractEntity<out ID : Serializable> : IdentifiablePersistable<ID> {

    @Transient
    @JsonIgnore
    override fun isNew(): Boolean {
        return null == getIdentity()
    }


    override fun toString(): String {
        return String.format("Entity of type %s with identity: %s", this.javaClass.name, getIdentity())
    }


    override fun equals(other: Any?): Boolean {

        if (null == other) {
            return false
        }

        if (this === other) {
            return true
        }

        if (javaClass != ClassUtils.getUserClass(other)) {
            return false
        }

        val that = other as AbstractEntity<*>?

        return if (null == this.getIdentity()) false else this.getIdentity() == that!!.getIdentity()
    }


    override fun hashCode(): Int {

        var hashCode = 17

        hashCode += if (null == getIdentity()) 0 else getIdentity()!!.hashCode() * 31

        return hashCode
    }

    companion object {

        private val serialVersionUID = -5554308939380869754L
    }
}

