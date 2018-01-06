import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../shared';

import { StockBookComponent } from './';
import { StockBookRoutingModule } from './stockbook-routing.module';
import { StockBookEntryModule } from './stock-book-entry/stock-book-entry.module';

@NgModule({
    imports: [
        SharedModule,
        StockBookEntryModule,
        StockBookRoutingModule
    ],
    declarations: [
        StockBookComponent,
    ],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StockBookModule {
}
