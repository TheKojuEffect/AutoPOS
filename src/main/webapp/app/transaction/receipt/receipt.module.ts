import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../../shared';

import {
    ReceiptComponent,
    ReceiptDeleteDialogComponent,
    ReceiptDeletePopupComponent,
    ReceiptDetailComponent,
    ReceiptDialogComponent,
    ReceiptPopupComponent,
    ReceiptPopupService,
    ReceiptResolvePagingParams,
    ReceiptRoutingModule,
    ReceiptService
} from './';

@NgModule({
    imports: [
        SharedModule,
        ReceiptRoutingModule
    ],
    declarations: [
        ReceiptComponent,
        ReceiptDetailComponent,
        ReceiptDialogComponent,
        ReceiptDeleteDialogComponent,
        ReceiptPopupComponent,
        ReceiptDeletePopupComponent,
    ],
    entryComponents: [
        ReceiptComponent,
        ReceiptDialogComponent,
        ReceiptPopupComponent,
        ReceiptDeleteDialogComponent,
        ReceiptDeletePopupComponent,
    ],
    providers: [
        ReceiptService,
        ReceiptPopupService,
        ReceiptResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReceiptModule {
}
