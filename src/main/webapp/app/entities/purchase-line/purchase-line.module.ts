import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutoPosSharedModule } from '../../shared';

import {
    PurchaseLineService,
    PurchaseLinePopupService,
    PurchaseLineComponent,
    PurchaseLineDetailComponent,
    PurchaseLineDialogComponent,
    PurchaseLinePopupComponent,
    PurchaseLineDeletePopupComponent,
    PurchaseLineDeleteDialogComponent,
    purchaseLineRoute,
    purchaseLinePopupRoute,
    PurchaseLineResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...purchaseLineRoute,
    ...purchaseLinePopupRoute,
];

@NgModule({
    imports: [
        AutoPosSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
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
export class AutoPosPurchaseLineModule {}
