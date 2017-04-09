import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../../shared';

import {
    PurchaseComponent,
    PurchaseDeleteDialogComponent,
    PurchaseDeletePopupComponent,
    PurchaseDetailComponent,
    PurchaseDialogComponent,
    PurchasePopupComponent,
    PurchasePopupService,
    PurchaseResolvePagingParams,
    PurchaseService
} from './';
import { PurchaseRoutingModule } from './purchase-routing.module';


@NgModule({
    imports: [
        SharedModule,
        PurchaseRoutingModule
    ],
    declarations: [
        PurchaseComponent,
        PurchaseDetailComponent,
        PurchaseDialogComponent,
        PurchaseDeleteDialogComponent,
        PurchasePopupComponent,
        PurchaseDeletePopupComponent,
    ],
    entryComponents: [
        PurchaseComponent,
        PurchaseDialogComponent,
        PurchasePopupComponent,
        PurchaseDeleteDialogComponent,
        PurchaseDeletePopupComponent,
    ],
    providers: [
        PurchaseService,
        PurchasePopupService,
        PurchaseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PurchaseModule {
}
