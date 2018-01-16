import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';
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

const ENTITY_STATES = [
    ...priceHistoryRoute,
    ...priceHistoryPopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(ENTITY_STATES)
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
export class AutoPosPriceHistoryModule {
}
