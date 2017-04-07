import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';

import {
    PurchaseComponent,
    PurchaseDeleteDialogComponent,
    PurchaseDeletePopupComponent,
    PurchaseDetailComponent,
    PurchaseDialogComponent,
    PurchasePopupComponent,
    purchasePopupRoute,
    PurchasePopupService,
    PurchaseResolvePagingParams,
    purchaseRoute,
    PurchaseService
} from './';

let ENTITY_STATES = [
    ...purchaseRoute,
    ...purchasePopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true})
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
