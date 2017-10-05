import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../../shared';
import {
    DayBookEntryComponent,
    DayBookEntryDeleteDialogComponent,
    DayBookEntryDeletePopupComponent,
    DayBookEntryDetailComponent,
    DayBookEntryDialogComponent,
    DayBookEntryPopupComponent,
    DayBookEntryPopupService,
    DayBookEntryResolvePagingParams,
    DayBookEntryRoutingModule,
    DayBookEntryService
} from './';

@NgModule({
    imports: [
        SharedModule,
        DayBookEntryRoutingModule
    ],
    declarations: [
        DayBookEntryComponent,
        DayBookEntryDetailComponent,
        DayBookEntryDialogComponent,
        DayBookEntryDeleteDialogComponent,
        DayBookEntryPopupComponent,
        DayBookEntryDeletePopupComponent,
    ],
    entryComponents: [
        DayBookEntryComponent,
        DayBookEntryDialogComponent,
        DayBookEntryPopupComponent,
        DayBookEntryDeleteDialogComponent,
        DayBookEntryDeletePopupComponent,
    ],
    providers: [
        DayBookEntryService,
        DayBookEntryPopupService,
        DayBookEntryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DayBookEntryModule {
}
