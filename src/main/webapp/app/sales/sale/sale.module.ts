import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import {
    SaleComponent,
    SaleDeleteDialogComponent,
    SaleDeletePopupComponent,
    SaleDetailComponent,
    SaleDialogComponent,
    SalePopupComponent,
    SalePopupService,
    SaleResolvePagingParams,
    SaleService
} from './';
import { SharedModule } from '../../shared';
import { SaleRoutingModule } from './sale-routing.module';
import { SalesComponent } from './sales.component';
import { SaleLineEntryComponent } from './';

@NgModule({
    imports: [
        SharedModule,
        SaleRoutingModule
    ],
    declarations: [
        SalesComponent,
        SaleComponent,
        SaleDetailComponent,
        SaleDialogComponent,
        SaleDeleteDialogComponent,
        SalePopupComponent,
        SaleDeletePopupComponent,
        SaleLineEntryComponent,
    ],
    entryComponents: [
        SalesComponent,
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
export class SaleModule {
}
