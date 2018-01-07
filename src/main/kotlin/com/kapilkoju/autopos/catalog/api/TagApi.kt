package com.kapilkoju.autopos.catalog.api

import com.kapilkoju.autopos.catalog.domain.Tag
import com.kapilkoju.autopos.catalog.service.TagRepo
import com.kapilkoju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(TagApi.baseUrl)
class TagApi(tagRepo: TagRepo) : BaseApi<Tag>(tagRepo, "tag", TagApi.baseUrl) {

    companion object {
        const val baseUrl = "/api/tags"
    }
}