package com.kapilkoju.autopos.catalog.service

import com.kapilkoju.autopos.catalog.domain.Category
import com.kapilkoju.autopos.kernel.service.RepoSpecification
import org.springframework.beans.factory.annotation.Autowired

class CategoryRepoSpec extends RepoSpecification {

    @Autowired
    CategoryRepo categoryRepo

    def "short name is capitalized while saving and updating"() {

        given: "A category with lowercase short name"
        def category = new Category()
        category.shortName = "mn"
        category.name = "Mahindra Nissan"

        when: "category is saved"
        categoryRepo.save(category)

        then: "short name is capitalized"
        category.id != null
        category.shortName == "MN"

        when: "short name is updated to lower case and updated"
        category.shortName = "an"
        categoryRepo.saveAndFlush(category)

        then: "short name should be capitalized"
        def updatedCategory = categoryRepo.findOne(category.id)
        updatedCategory.shortName == "AN"
    }
}
