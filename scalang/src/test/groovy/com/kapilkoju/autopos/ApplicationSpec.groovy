package com.kapilkoju.autopos

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification


@SpringBootTest(classes = Application)
@ContextConfiguration
class ApplicationSpec extends Specification {

    @Autowired
    ApplicationContext context

    def "context loads"() {
        expect:
        context != null
    }
}
