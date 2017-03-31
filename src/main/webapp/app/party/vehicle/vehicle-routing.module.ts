import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { vehiclePopupRoutes } from './';

@NgModule({
    imports: [
        RouterModule.forChild(vehiclePopupRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class VehicleRoutingModule {
}
