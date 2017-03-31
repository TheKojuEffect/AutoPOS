import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { customerPopupRoutes } from './';

@NgModule({
    imports: [
        RouterModule.forChild(customerPopupRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class CustomerRoutingModule {
}
