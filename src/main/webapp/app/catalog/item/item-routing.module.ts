import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { itemPopupRoutes } from './';

@NgModule({
    imports: [
        RouterModule.forChild(itemPopupRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class ItemRoutingModule {
}
