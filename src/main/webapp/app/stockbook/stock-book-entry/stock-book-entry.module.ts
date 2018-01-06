import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../../shared';
import {
    StockBookEntryComponent,
    StockBookEntryDeleteDialogComponent,
    StockBookEntryDeletePopupComponent,
    StockBookEntryDetailComponent,
    StockBookEntryDialogComponent,
    StockBookEntryPopupComponent,
    StockBookEntryPopupService,
    StockBookEntryResolvePagingParams,
    StockBookEntryRoutingModule,
    StockBookEntryService
} from './';

@NgModule({
    imports: [
        SharedModule,
        StockBookEntryRoutingModule
    ],
    declarations: [
        StockBookEntryComponent,
        StockBookEntryDetailComponent,
        StockBookEntryDialogComponent,
        StockBookEntryDeleteDialogComponent,
        StockBookEntryPopupComponent,
        StockBookEntryDeletePopupComponent,
    ],
    entryComponents: [
        StockBookEntryComponent,
        StockBookEntryDialogComponent,
        StockBookEntryPopupComponent,
        StockBookEntryDeleteDialogComponent,
        StockBookEntryDeletePopupComponent,
    ],
    providers: [
        StockBookEntryService,
        StockBookEntryPopupService,
        StockBookEntryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StockBookEntryModule {
}
