package io.koju.autopos.catalog.service

import scala.annotation.tailrec

object ItemCode {

  private val alpha = "3KMEQPNHABTGCWUVRYZFSXJD"

  private val base = alpha.length

  private val firstChar = alpha.charAt(0)

  def apply(code: String) = decode(code)

  def apply(id: Long) = encode(id)

  def encode(id: Long) = {

    @tailrec
    def enc(in: Long, acc: String): String =
      if (in < 1) acc
      else enc(in / base, alpha((in % base).toInt) + acc)

    enc(id, "")
  }

  def decode: PartialFunction[String, Long] = {
    case code: String if code.length == 0 => 0
    case code: String if code.head != firstChar => {
      val in = code.reverse

      @tailrec
      def dec(idx: Int, acc: BigInt): Long =
        if (idx == in.length) acc.toLong
        else dec(idx + 1, acc + alpha.indexOf(in(idx)) * BigInt(base).pow(idx))

      dec(0, 0)
    }
  }
}
