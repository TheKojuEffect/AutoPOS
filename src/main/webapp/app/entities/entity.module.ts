import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { AutoPosBrandModule } from './brand/brand.module';
import { AutoPosCategoryModule } from './category/category.module';
import { AutoPosTagModule } from './tag/tag.module';
import { AutoPosPriceHistoryModule } from './price-history/price-history.module';
import { AutoPosPhoneModule } from './phone/phone.module';
import { AutoPosVendorModule } from './vendor/vendor.module';
import { AutoPosCustomerModule } from './customer/customer.module';
import { AutoPosVehicleModule } from './vehicle/vehicle.module';
import { AutoPosDayBookEntryModule } from './day-book-entry/day-book-entry.module';
import { AutoPosSaleModule } from './sale/sale.module';
import { AutoPosPurchaseModule } from './purchase/purchase.module';
import { AutoPosPurchaseLineModule } from './purchase-line/purchase-line.module';
import { AutoPosSaleLineModule } from './sale-line/sale-line.module';
import { AutoPosPaymentModule } from './payment/payment.module';
import { AutoPosReceiptModule } from './receipt/receipt.module';

@NgModule({
    imports: [
        AutoPosBrandModule,
        AutoPosCategoryModule,
        AutoPosTagModule,
        AutoPosPriceHistoryModule,
        AutoPosPhoneModule,
        AutoPosVendorModule,
        AutoPosCustomerModule,
        AutoPosVehicleModule,
        AutoPosDayBookEntryModule,
        AutoPosSaleModule,
        AutoPosPurchaseModule,
        AutoPosPurchaseLineModule,
        AutoPosSaleLineModule,
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
