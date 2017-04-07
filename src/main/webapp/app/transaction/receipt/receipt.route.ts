import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { PaginationUtil } from 'ng-jhipster';
import { UserRouteAccessService } from '../../shared/auth/user-route-access-service';

import { ReceiptComponent } from './receipt.component';
import { ReceiptDetailComponent } from './receipt-detail.component';
import { ReceiptPopupComponent } from './receipt-dialog.component';
import { ReceiptDeletePopupComponent } from './receipt-delete-dialog.component';

@Injectable()
export class ReceiptResolvePagingParams implements Resolve<any> {

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

export const receiptRoutes: Routes = [
    {
        path: 'receipt',
        component: ReceiptComponent,
        resolve: {
            'pagingParams': ReceiptResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.receipt.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'receipt/:id',
        component: ReceiptDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.receipt.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const receiptPopupRoutes: Routes = [
    {
        path: 'receipt-new',
        component: ReceiptPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.receipt.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'receipt/:id/edit',
        component: ReceiptPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.receipt.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'receipt/:id/delete',
        component: ReceiptDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.receipt.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
