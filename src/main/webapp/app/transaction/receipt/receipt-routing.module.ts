import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { receiptPopupRoutes } from './';

@NgModule({
    imports: [
        RouterModule.forChild(receiptPopupRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class ReceiptRoutingModule {
}
