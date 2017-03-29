import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../../shared';

import { ItemComponent, ItemDetailComponent, ItemPopupService, ItemResolvePagingParams, ItemService } from './';
import { ItemDeleteDialogComponent, ItemDeletePopupComponent } from './item-delete-dialog.component';
import { ItemDialogComponent, ItemPopupComponent } from './item-dialog.component';
import { ItemRoutingModule } from './item-routing.module';


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
    ],
    entryComponents: [
        ItemComponent,
        ItemDialogComponent,
        ItemPopupComponent,
        ItemDeleteDialogComponent,
        ItemDeletePopupComponent,
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
