import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';

import {
    SaleLineService,
    SaleLinePopupService,
    SaleLineComponent,
    SaleLineDetailComponent,
    SaleLineDialogComponent,
    SaleLinePopupComponent,
    SaleLineDeletePopupComponent,
    SaleLineDeleteDialogComponent,
    saleLineRoute,
    saleLinePopupRoute,
    SaleLineResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...saleLineRoute,
    ...saleLinePopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SaleLineComponent,
        SaleLineDetailComponent,
        SaleLineDialogComponent,
        SaleLineDeleteDialogComponent,
        SaleLinePopupComponent,
        SaleLineDeletePopupComponent,
    ],
    entryComponents: [
        SaleLineComponent,
        SaleLineDialogComponent,
        SaleLinePopupComponent,
        SaleLineDeleteDialogComponent,
        SaleLineDeletePopupComponent,
    ],
    providers: [
        SaleLineService,
        SaleLinePopupService,
        SaleLineResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoPosSaleLineModule {}
