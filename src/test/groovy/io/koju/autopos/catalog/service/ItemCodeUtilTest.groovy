package io.koju.autopos.catalog.service

import io.koju.autopos.Application
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
class ItemCodeUtilTest extends Specification {

    @Autowired
    JdbcTemplate jdbcTemplate

    def "ItemCode getId and getCode process is working and symmetric"() {

        expect:
        ItemCodeUtil.getCode(id) == code

        and:
        ItemCodeUtil.getId(code) == id

        where:
        id                   || code
        1L                   || 'K'
        2L                   || 'M'
        1000L                || 'KYR'
        23132L               || 'KRES'
        5635254252L          || 'KPGYMFCC'
        99427982344L         || 'XRNSKRXR'
        9223372036854775807L || 'TCWHHMFDCBX3XH'
    }

    def "ItemCode.getCode is consistent with item_code function in DB"() {

        expect:
        ItemCodeUtil.getCode(id) == jdbcTemplate.queryForObject("SELECT item_code(?);", String, id)

        where:
        id                   | _
        1L                   | _
        22L                  | _
        1000L                | _
        10272L               | _
        231132L              | _
        5635254252L          | _
        994272982344L        | _
        9223372036854775807L | _

    }
}
