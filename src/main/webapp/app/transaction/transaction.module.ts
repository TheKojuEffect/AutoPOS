import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../shared';

import { TransactionComponent } from './';
import { TransactionRoutingModule } from './transaction-routing.module';
import { ReceiptModule } from './receipt/receipt.module';
import { PaymentModule } from './payment/payment.module';

@NgModule({
    imports: [
        SharedModule,
        ReceiptModule,
        PaymentModule,
        TransactionRoutingModule
    ],
    declarations: [
        TransactionComponent,
    ],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TransactionModule {
}
