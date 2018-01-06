import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { stockBookEntryPopupRoutes } from '.';

@NgModule({
    imports: [
        RouterModule.forChild(stockBookEntryPopupRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class StockBookEntryRoutingModule {
}
