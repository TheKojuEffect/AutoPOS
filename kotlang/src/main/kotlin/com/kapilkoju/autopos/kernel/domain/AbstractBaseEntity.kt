package com.kapilkoju.autopos.kernel.domain

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractBaseEntity : AbstractEntity<Long>(), BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @JsonIgnore
    override fun getIdentity(): Long? {
        return getId()
    }

    override fun getId(): Long? {
        return id
    }

    fun setId(id: Long?) {
        this.id = id
    }

}
