import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { paymentPopupRoutes } from './';

@NgModule({
    imports: [
        RouterModule.forChild(paymentPopupRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class PaymentRoutingModule {
}
