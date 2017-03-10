import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutoPosSharedModule } from '../../shared';

import {
    PriceHistoryService,
    PriceHistoryPopupService,
    PriceHistoryComponent,
    PriceHistoryDetailComponent,
    PriceHistoryDialogComponent,
    PriceHistoryPopupComponent,
    PriceHistoryDeletePopupComponent,
    PriceHistoryDeleteDialogComponent,
    priceHistoryRoute,
    priceHistoryPopupRoute,
} from './';

let ENTITY_STATES = [
    ...priceHistoryRoute,
    ...priceHistoryPopupRoute,
];

@NgModule({
    imports: [
        AutoPosSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PriceHistoryComponent,
        PriceHistoryDetailComponent,
        PriceHistoryDialogComponent,
        PriceHistoryDeleteDialogComponent,
        PriceHistoryPopupComponent,
        PriceHistoryDeletePopupComponent,
    ],
    entryComponents: [
        PriceHistoryComponent,
        PriceHistoryDialogComponent,
        PriceHistoryPopupComponent,
        PriceHistoryDeleteDialogComponent,
        PriceHistoryDeletePopupComponent,
    ],
    providers: [
        PriceHistoryService,
        PriceHistoryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoPosPriceHistoryModule {}
