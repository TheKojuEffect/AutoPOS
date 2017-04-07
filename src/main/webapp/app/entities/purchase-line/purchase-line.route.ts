import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { PaginationUtil } from 'ng-jhipster';

import { PurchaseLineComponent } from './purchase-line.component';
import { PurchaseLineDetailComponent } from './purchase-line-detail.component';
import { PurchaseLinePopupComponent } from './purchase-line-dialog.component';
import { PurchaseLineDeletePopupComponent } from './purchase-line-delete-dialog.component';

@Injectable()
export class PurchaseLineResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

export const purchaseLineRoute: Routes = [
    {
        path: 'purchase-line',
        component: PurchaseLineComponent,
        resolve: {
            'pagingParams': PurchaseLineResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.purchaseLine.home.title'
        }
    }, {
        path: 'purchase-line/:id',
        component: PurchaseLineDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.purchaseLine.home.title'
        }
    }
];

export const purchaseLinePopupRoute: Routes = [
    {
        path: 'purchase-line-new',
        component: PurchaseLinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.purchaseLine.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'purchase-line/:id/edit',
        component: PurchaseLinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.purchaseLine.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'purchase-line/:id/delete',
        component: PurchaseLineDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.purchaseLine.home.title'
        },
        outlet: 'popup'
    }
];
