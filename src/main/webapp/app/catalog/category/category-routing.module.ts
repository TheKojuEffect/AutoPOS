import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { categoryPopupRoutes } from './';

@NgModule({
    imports: [
        RouterModule.forChild(categoryPopupRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class CategoryRoutingModule {
}
