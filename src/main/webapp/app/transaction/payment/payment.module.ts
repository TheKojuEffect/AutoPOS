import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';

import {
    PaymentService,
    PaymentPopupService,
    PaymentComponent,
    PaymentDetailComponent,
    PaymentDialogComponent,
    PaymentPopupComponent,
    PaymentDeletePopupComponent,
    PaymentDeleteDialogComponent,
    PaymentResolvePagingParams,
    PaymentRoutingModule
} from './';


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
