import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { TransactionComponent } from './';
import { NgModule } from '@angular/core';
import { receiptRoutes } from './receipt';
import { paymentRoutes } from './payment';

export const transactionRoutes: Routes = [
    {
        path: 'transaction',
        component: TransactionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.transaction.title'
        },
        canActivate: [UserRouteAccessService],
        children: [
            ...receiptRoutes,
            ...paymentRoutes
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(transactionRoutes)

    ],
    exports: [
        RouterModule
    ]
})
export class TransactionRoutingModule {
}
