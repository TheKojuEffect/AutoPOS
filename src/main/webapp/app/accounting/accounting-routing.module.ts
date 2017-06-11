import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { AccountingComponent } from './';
import { NgModule } from '@angular/core';
import { dayBookEntryRoutes } from './day-book-entry';

export const accountingRoutes: Routes = [
    {
        path: 'accounting',
        component: AccountingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.accounting.title'
        },
        canActivate: [UserRouteAccessService],
        children: [
            ...dayBookEntryRoutes
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(accountingRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class AccountingRoutingModule {
}
