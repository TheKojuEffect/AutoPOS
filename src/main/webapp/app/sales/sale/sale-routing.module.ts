import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { salePopupRoutes, saleRoutes } from './sale.route';

@NgModule({
    imports: [RouterModule.forChild([
        ...saleRoutes,
        ...salePopupRoutes
    ])],
    exports: [RouterModule]
})
export class SaleRoutingModule {
}
