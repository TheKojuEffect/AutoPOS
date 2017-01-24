package com.kapilkoju.autopos.catalog.service

import com.kapilkoju.autopos.catalog.domain.Brand
import com.kapilkoju.autopos.catalog.domain.Category
import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.kernel.service.RepoSpecification
import org.hibernate.exception.ConstraintViolationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException


class ItemRepoSpec extends RepoSpecification {

    @Autowired
    ItemRepo itemRepo

    @Autowired
    BrandRepo brandRepo

    @Autowired
    CategoryRepo categoryRepo

    def 'Item name should be unique if category and brand is not set'() {

        given: 'An item exists with a name'
        def item1 = new Item()
        item1.name = "Test Item"
        item1.setMarkedPrice(20.0)
        itemRepo.save(item1)

        when: 'Another item is saved with same name'
        def item2 = new Item()
        item2.name = "Test Item"
        item2.setMarkedPrice(30.0)
        itemRepo.save(item2)

        then: 'An DataIntegrityViolationException should occur'
        DataIntegrityViolationException ex = thrown()
        def cause = ex.getCause()
        assert cause instanceof ConstraintViolationException
        assert cause.constraintName == 'item_name_category_branch_key'
    }

    def 'Item name, category and brand should be unique'() {

        given: 'An item exists with a name, brand and category'
        def brand = new Brand()
        brand.name = 'Brand'
        brandRepo.save(brand)

        def category = new Category()
        category.shortName = 'C1'
        category.name = 'Cat'
        categoryRepo.save(category)

        def item1 = new Item()
        item1.name = "Test Item"
        item1.setMarkedPrice(20.0)
        item1.brand = brand
        item1.category = category
        itemRepo.save(item1)

        when: 'Another item is saved with same name, brand and category'
        def item2 = new Item()
        item2.name = "Test Item"
        item2.setMarkedPrice(30.0)
        item2.brand = brand
        item2.category = category
        itemRepo.save(item2)

        then: 'An DataIntegrityViolationException should occur'
        DataIntegrityViolationException ex = thrown()
        def cause = ex.getCause()
        assert cause instanceof ConstraintViolationException
        assert cause.constraintName == 'item_name_category_branch_key'
    }
}
