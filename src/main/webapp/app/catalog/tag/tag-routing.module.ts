import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { tagPopupRoutes } from './';

@NgModule({
    imports: [
        RouterModule.forChild(tagPopupRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class TagRoutingModule {
}
