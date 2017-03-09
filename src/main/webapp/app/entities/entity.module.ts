import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AutoPosBrandModule } from './brand/brand.module';
import { AutoPosCategoryModule } from './category/category.module';
import { AutoPosTagModule } from './tag/tag.module';
import { AutoPosItemModule } from './item/item.module';
import { AutoPosPriceHistoryModule } from './price-history/price-history.module';
import { AutoPosPhoneModule } from './phone/phone.module';
import { AutoPosVendorModule } from './vendor/vendor.module';
import { AutoPosCustomerModule } from './customer/customer.module';
import { AutoPosVehicleModule } from './vehicle/vehicle.module';
import { AutoPosDayBookEntryModule } from './day-book-entry/day-book-entry.module';
import { AutoPosPurchaseModule } from './purchase/purchase.module';
import { AutoPosPurchaseLineModule } from './purchase-line/purchase-line.module';
import { AutoPosSaleLineModule } from './sale-line/sale-line.module';
import { AutoPosSaleModule } from './sale/sale.module';
import { AutoPosPaymentModule } from './payment/payment.module';
import { AutoPosReceiptModule } from './receipt/receipt.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AutoPosBrandModule,
        AutoPosCategoryModule,
        AutoPosTagModule,
        AutoPosItemModule,
        AutoPosPriceHistoryModule,
        AutoPosPhoneModule,
        AutoPosVendorModule,
        AutoPosCustomerModule,
        AutoPosVehicleModule,
        AutoPosDayBookEntryModule,
        AutoPosPurchaseModule,
        AutoPosPurchaseLineModule,
        AutoPosSaleLineModule,
        AutoPosSaleModule,
        AutoPosPaymentModule,
        AutoPosReceiptModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoPosEntityModule {}
