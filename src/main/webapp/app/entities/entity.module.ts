import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { AutoPosPhoneModule } from './phone/phone.module';
import { AutoPosVendorModule } from './vendor/vendor.module';
import { AutoPosCustomerModule } from './customer/customer.module';
import { AutoPosVehicleModule } from './vehicle/vehicle.module';
import { AutoPosSaleModule } from './sale/sale.module';
import { AutoPosPurchaseModule } from './purchase/purchase.module';
import { AutoPosPurchaseLineModule } from './purchase-line/purchase-line.module';
import { AutoPosSaleLineModule } from './sale-line/sale-line.module';
import { AutoPosPaymentModule } from './payment/payment.module';
import { AutoPosReceiptModule } from './receipt/receipt.module';

@NgModule({
    imports: [
        AutoPosPhoneModule,
        AutoPosVendorModule,
        AutoPosCustomerModule,
        AutoPosVehicleModule,
        AutoPosSaleModule,
        AutoPosPurchaseModule,
        AutoPosPurchaseLineModule,
        AutoPosSaleLineModule,
        AutoPosPaymentModule,
        AutoPosReceiptModule,
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoPosEntityModule {
}
