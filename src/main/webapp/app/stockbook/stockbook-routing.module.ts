import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { StockBookComponent } from './';
import { NgModule } from '@angular/core';
import { stockBookEntryRoutes } from './stock-book-entry';

export const stockBookRoutes: Routes = [
    {
        path: 'stockbook',
        component: StockBookComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.stockBook.title'
        },
        canActivate: [UserRouteAccessService],
        children: [
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'stock-book-entry'
            },
            ...stockBookEntryRoutes
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(stockBookRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class StockBookRoutingModule {
}
