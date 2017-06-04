import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { PurchaseComponent, PurchaseDetailComponent, PurchaseResolvePagingParams, PurchaseService } from './';
import { SharedModule } from '../shared';
import { PurchaseRoutingModule } from './purchase-routing.module';
import { ItemModule } from '../catalog/item/item.module';
import { PartyModule } from '../party/party.module';
import { PurchaseLineService } from './purchase-line.service';

@NgModule({
    imports: [
        SharedModule,
        PurchaseRoutingModule,
        ItemModule,
        PartyModule,
    ],
    declarations: [
        PurchaseComponent,
        PurchaseDetailComponent,
    ],
    entryComponents: [
        PurchaseComponent,
    ],
    providers: [
        PurchaseService,
        PurchaseLineService,
        PurchaseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PurchaseModule {
}
