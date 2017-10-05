import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../../shared';

import {
    VehicleComponent,
    VehicleDeleteDialogComponent,
    VehicleDeletePopupComponent,
    VehicleDetailComponent,
    VehicleDialogComponent,
    VehiclePopupComponent,
    VehiclePopupService,
    VehicleResolvePagingParams,
    VehicleRoutingModule,
    VehicleService,
} from './';

@NgModule({
    imports: [
        SharedModule,
        VehicleRoutingModule
    ],
    declarations: [
        VehicleComponent,
        VehicleDetailComponent,
        VehicleDialogComponent,
        VehicleDeleteDialogComponent,
        VehiclePopupComponent,
        VehicleDeletePopupComponent,
    ],
    entryComponents: [
        VehicleComponent,
        VehicleDialogComponent,
        VehiclePopupComponent,
        VehicleDeleteDialogComponent,
        VehicleDeletePopupComponent,
    ],
    providers: [
        VehicleService,
        VehiclePopupService,
        VehicleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VehicleModule {
}
