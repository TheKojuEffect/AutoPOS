package io.koju.autopos.catalog.service

import spock.lang.Specification

class ItemCodeTest extends Specification {

    def "ItemCode getId and getCode process is working and symmetric"() {

        expect:
        ItemCode.getCode(id) == code

        and:
        ItemCode.getId(code) == id

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
}
