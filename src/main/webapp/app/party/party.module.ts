import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../shared';

import { PartyComponent } from './';
import { PartyRoutingModule } from './party-routing.module';
import { CustomerModule } from './customer/customer.module';
import { VehicleModule } from './vehicle/vehicle.module';
import { VendorModule } from './vendor/vendor.module';

@NgModule({
    imports: [
        SharedModule,
        CustomerModule,
        VehicleModule,
        VendorModule,
        PartyRoutingModule
    ],
    declarations: [
        PartyComponent,
    ],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PartyModule {
}
