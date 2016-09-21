package io.koju.autopos.catalog.service

import com.github.scalaspring.scalatest.TestContextManagement
import io.koju.autopos.test.autoconfigure.jdbc.DataJdbcTest
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.{PropertyChecks, TableDrivenPropertyChecks}
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase._
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration

@RunWith(classOf[JUnitRunner])
class ItemCodeSpec extends FlatSpec with TableDrivenPropertyChecks with Matchers {

  "ItemCode encode/decode" should "be symmetric" in {
    val idCodes =
      Table(
        ("id", "code"),
        (1L, "K"),
        (2L, "M"),
        (1000L, "KYR"),
        (23132L, "KRES"),
        (5635254252L, "KPGYMFCC"),
        (99427982344L, "XRNSKRXR"),
        (9223372036854775807L, "TCWHHMFDCBX3XH")
      )

    forAll(idCodes) { (id: Long, code: String) =>
      ItemCode(id) should equal(code)
      ItemCode(code) should equal(id)
    }
  }
}


@RunWith(classOf[JUnitRunner])
@DataJdbcTest
@ContextConfiguration(classes = Array(classOf[ItemCodeDbSpec]))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ItemCodeDbSpec extends FlatSpec with TestContextManagement with PropertyChecks with Matchers {

  @Autowired
  val jdbcTemplate: JdbcTemplate = null

  "Item Code encode" should "be consistent with DB item_code" in {
    forAll { (id: Long) =>
      whenever(id > 0) {
        val code = jdbcTemplate.queryForObject("SELECT item_code(?);", classOf[String], id.asInstanceOf[java.lang.Long])
        ItemCode(id) should equal(code)
      }
    }
  }
}