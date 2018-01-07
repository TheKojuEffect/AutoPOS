package com.kapilkoju.autopos.kernel.service

import com.kapilkoju.autopos.Application
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification


@SpringBootTest(classes = Application)
@WithUserDetails("admin")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
abstract class RepoSpecification extends Specification {
}
