import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../../shared';
import {
    CostPriceDialogComponent,
    CostPricePopupComponent,
    ItemComponent,
    ItemDeleteDialogComponent,
    ItemDeletePopupComponent,
    ItemDetailComponent,
    ItemDialogComponent,
    ItemPopupComponent,
    ItemPopupService,
    ItemResolvePagingParams,
    ItemRoutingModule,
    ItemService,
} from './';


@NgModule({
    imports: [
        SharedModule,
        ItemRoutingModule
    ],
    declarations: [
        ItemComponent,
        ItemDetailComponent,
        ItemDialogComponent,
        ItemDeleteDialogComponent,
        ItemPopupComponent,
        ItemDeletePopupComponent,
        CostPriceDialogComponent,
        CostPricePopupComponent,
    ],
    entryComponents: [
        ItemComponent,
        ItemDialogComponent,
        ItemPopupComponent,
        ItemDeleteDialogComponent,
        ItemDeletePopupComponent,
        CostPriceDialogComponent,
        CostPricePopupComponent,
    ],
    providers: [
        ItemService,
        ItemPopupService,
        ItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItemModule {
}
