import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { brandPopupRoutes } from './';

@NgModule({
    imports: [
        RouterModule.forChild(brandPopupRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class BrandRoutingModule {
}
