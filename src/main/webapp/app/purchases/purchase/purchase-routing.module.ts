import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { purchasePopupRoute, purchaseRoute } from './purchase.route';

@NgModule({
    imports: [
        RouterModule.forChild([
            ...purchaseRoute,
            ...purchasePopupRoute
        ])
    ],
    exports: [
        RouterModule
    ]
})
export class PurchaseRoutingModule {
}
