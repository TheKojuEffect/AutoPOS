import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../shared';

import { AccountingComponent } from './';
import { AccountingRoutingModule } from './accounting-routing.module';
import { DayBookEntryModule } from './day-book-entry/day-book-entry.module';

@NgModule({
    imports: [
        SharedModule,
        DayBookEntryModule,
        AccountingRoutingModule
    ],
    declarations: [
        AccountingComponent,
    ],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountingModule {
}
