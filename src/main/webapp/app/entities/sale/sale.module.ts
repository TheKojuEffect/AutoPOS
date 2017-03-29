import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';

import {
    SaleService,
    SalePopupService,
    SaleComponent,
    SaleDetailComponent,
    SaleDialogComponent,
    SalePopupComponent,
    SaleDeletePopupComponent,
    SaleDeleteDialogComponent,
    saleRoute,
    salePopupRoute,
    SaleResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...saleRoute,
    ...salePopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SaleComponent,
        SaleDetailComponent,
        SaleDialogComponent,
        SaleDeleteDialogComponent,
        SalePopupComponent,
        SaleDeletePopupComponent,
    ],
    entryComponents: [
        SaleComponent,
        SaleDialogComponent,
        SalePopupComponent,
        SaleDeleteDialogComponent,
        SaleDeletePopupComponent,
    ],
    providers: [
        SaleService,
        SalePopupService,
        SaleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoPosSaleModule {}
