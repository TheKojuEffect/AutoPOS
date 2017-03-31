import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { vendorPopupRoutes } from './';

@NgModule({
    imports: [
        RouterModule.forChild(vendorPopupRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class VendorRoutingModule {
}
