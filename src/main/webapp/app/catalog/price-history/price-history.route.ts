import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PriceHistoryComponent } from './price-history.component';
import { PriceHistoryDetailComponent } from './price-history-detail.component';
import { PriceHistoryPopupComponent } from './price-history-dialog.component';
import { PriceHistoryDeletePopupComponent } from './price-history-delete-dialog.component';

export const priceHistoryRoute: Routes = [
    {
        path: 'price-history',
        component: PriceHistoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.priceHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'price-history/:id',
        component: PriceHistoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.priceHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const priceHistoryPopupRoute: Routes = [
    {
        path: 'price-history-new',
        component: PriceHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.priceHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'price-history/:id/edit',
        component: PriceHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.priceHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'price-history/:id/delete',
        component: PriceHistoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.priceHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
