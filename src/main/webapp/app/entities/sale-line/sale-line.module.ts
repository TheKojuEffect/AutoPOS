import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';

import {
    SaleLineComponent,
    SaleLineDeleteDialogComponent,
    SaleLineDeletePopupComponent,
    SaleLineDetailComponent,
    SaleLineDialogComponent,
    SaleLinePopupComponent,
    saleLinePopupRoute,
    SaleLinePopupService,
    SaleLineResolvePagingParams,
    saleLineRoute,
    SaleLineService
} from './';

let ENTITY_STATES = [
    ...saleLineRoute,
    ...saleLinePopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true})
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
export class AutoPosSaleLineModule {
}
