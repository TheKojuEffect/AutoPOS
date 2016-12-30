package com.kapilkoju.autopos.party

import com.kapilkoju.autopos.party.domain.Vendor
import com.kapilkoju.autopos.party.repo.VendorRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

trait VendorService {
  def getVendor(id: Long): Option[Vendor]
}


@Service
@Transactional
class VendorServiceImpl(private val vendorRepo: VendorRepo)
  extends VendorService {

  override def getVendor(id: Long): Option[Vendor] = Option(vendorRepo.getById(id))

}
