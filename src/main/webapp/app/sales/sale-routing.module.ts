import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { saleRoutes } from './sale.route';

@NgModule({
    imports: [RouterModule.forChild([
        ...saleRoutes,
    ])],
    exports: [RouterModule]
})
export class SaleRoutingModule {
}
