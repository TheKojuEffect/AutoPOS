import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {SaleComponent, SaleDetailComponent, SaleResolvePagingParams, SaleService} from './';
import {SharedModule} from '../../shared';
import {SaleRoutingModule} from './sale-routing.module';
import {SalesComponent} from './sales.component';
import {ItemModule} from '../../catalog/item/item.module';

@NgModule({
    imports: [
        SharedModule,
        SaleRoutingModule,
        ItemModule
    ],
    declarations: [
        SalesComponent,
        SaleComponent,
        SaleDetailComponent,
    ],
    entryComponents: [
        SalesComponent,
        SaleComponent,
    ],
    providers: [
        SaleService,
        SaleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaleModule {
}
