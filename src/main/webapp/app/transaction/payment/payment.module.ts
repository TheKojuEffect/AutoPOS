import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../../shared';

import {
    PaymentComponent,
    PaymentDeleteDialogComponent,
    PaymentDeletePopupComponent,
    PaymentDetailComponent,
    PaymentDialogComponent,
    PaymentPopupComponent,
    PaymentPopupService,
    PaymentResolvePagingParams,
    PaymentService
} from './';
import { PaymentRoutingModule } from './payment-routing.module';

@NgModule({
    imports: [
        SharedModule,
        PaymentRoutingModule
    ],
    declarations: [
        PaymentComponent,
        PaymentDetailComponent,
        PaymentDialogComponent,
        PaymentDeleteDialogComponent,
        PaymentPopupComponent,
        PaymentDeletePopupComponent,
    ],
    entryComponents: [
        PaymentComponent,
        PaymentDialogComponent,
        PaymentPopupComponent,
        PaymentDeleteDialogComponent,
        PaymentDeletePopupComponent,
    ],
    providers: [
        PaymentService,
        PaymentPopupService,
        PaymentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PaymentModule {
}
