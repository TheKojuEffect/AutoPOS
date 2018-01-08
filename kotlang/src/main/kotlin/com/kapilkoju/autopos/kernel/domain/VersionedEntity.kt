package com.kapilkoju.autopos.kernel.domain

import com.fasterxml.jackson.annotation.JsonView
import com.kapilkoju.autopos.kernel.json.Views

import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.Version

@MappedSuperclass
abstract class VersionedEntity : AbstractBaseEntity(), Versioned {

    @Version
    @Column(name = "version")
    @JsonView(Views.Versioned::class)
    private var version: Long? = null

    override fun getVersion(): Long? {
        return version
    }

    protected fun setVersion(version: Long?) {
        this.version = version
    }

}
