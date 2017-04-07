import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';

import {
    PurchaseLineComponent,
    PurchaseLineDeleteDialogComponent,
    PurchaseLineDeletePopupComponent,
    PurchaseLineDetailComponent,
    PurchaseLineDialogComponent,
    PurchaseLinePopupComponent,
    purchaseLinePopupRoute,
    PurchaseLinePopupService,
    PurchaseLineResolvePagingParams,
    purchaseLineRoute,
    PurchaseLineService
} from './';

let ENTITY_STATES = [
    ...purchaseLineRoute,
    ...purchaseLinePopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true})
    ],
    declarations: [
        PurchaseLineComponent,
        PurchaseLineDetailComponent,
        PurchaseLineDialogComponent,
        PurchaseLineDeleteDialogComponent,
        PurchaseLinePopupComponent,
        PurchaseLineDeletePopupComponent,
    ],
    entryComponents: [
        PurchaseLineComponent,
        PurchaseLineDialogComponent,
        PurchaseLinePopupComponent,
        PurchaseLineDeleteDialogComponent,
        PurchaseLineDeletePopupComponent,
    ],
    providers: [
        PurchaseLineService,
        PurchaseLinePopupService,
        PurchaseLineResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PurchaseLineModule {
}
