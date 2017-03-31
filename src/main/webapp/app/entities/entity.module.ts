import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { AutoPosSaleModule } from './sale/sale.module';
import { AutoPosPurchaseModule } from './purchase/purchase.module';
import { AutoPosPurchaseLineModule } from './purchase-line/purchase-line.module';
import { AutoPosSaleLineModule } from './sale-line/sale-line.module';

@NgModule({
    imports: [
        AutoPosSaleModule,
        AutoPosPurchaseModule,
        AutoPosPurchaseLineModule,
        AutoPosSaleLineModule,
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoPosEntityModule {
}
