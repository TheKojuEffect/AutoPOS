import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../../shared';

import {
    PriceHistoryComponent,
    PriceHistoryDeleteDialogComponent,
    PriceHistoryDeletePopupComponent,
    PriceHistoryDetailComponent,
    PriceHistoryDialogComponent,
    PriceHistoryPopupComponent,
    priceHistoryPopupRoute,
    PriceHistoryPopupService,
    priceHistoryRoute,
    PriceHistoryService
} from './';

let ENTITY_STATES = [
    ...priceHistoryRoute,
    ...priceHistoryPopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true})
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
