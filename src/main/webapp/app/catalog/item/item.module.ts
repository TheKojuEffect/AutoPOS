import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../../shared';

import { ItemComponent, ItemDetailComponent, ItemPopupService, ItemResolvePagingParams, ItemService } from './';
import { ItemDeleteDialogComponent, ItemDeletePopupComponent } from './item-delete-dialog.component';
import { ItemDialogComponent, ItemPopupComponent } from './item-dialog.component';
import { ItemRoutingModule } from './item-routing.module';
import { CostPriceDialogComponent, CostPricePopupComponent } from './cost-price.component';

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
        ItemResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItemModule {
}
