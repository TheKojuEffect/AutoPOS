import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { purchaseRoutes } from './purchase.route';

@NgModule({
    imports: [RouterModule.forChild([
        ...purchaseRoutes,
    ])],
    exports: [RouterModule]
})
export class PurchaseRoutingModule {
}
