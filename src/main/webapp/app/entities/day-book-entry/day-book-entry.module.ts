import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutoPosSharedModule } from '../../shared';

import {
    DayBookEntryService,
    DayBookEntryPopupService,
    DayBookEntryComponent,
    DayBookEntryDetailComponent,
    DayBookEntryDialogComponent,
    DayBookEntryPopupComponent,
    DayBookEntryDeletePopupComponent,
    DayBookEntryDeleteDialogComponent,
    dayBookEntryRoute,
    dayBookEntryPopupRoute,
    DayBookEntryResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...dayBookEntryRoute,
    ...dayBookEntryPopupRoute,
];

@NgModule({
    imports: [
        AutoPosSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
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
export class AutoPosDayBookEntryModule {}
