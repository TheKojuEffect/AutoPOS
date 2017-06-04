import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { PaginationUtil } from 'ng-jhipster';
import { PurchaseDetailComponent } from './purchase-detail.component';
import { UserRouteAccessService } from '../shared/auth/user-route-access-service';
import { PurchaseComponent } from './purchase.component';

@Injectable()
export class PurchaseResolvePagingParams implements Resolve<any> {

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

export const purchaseRoutes: Routes = [
    {
        path: 'purchase',
        component: PurchaseComponent,
        resolve: {
            'pagingParams': PurchaseResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.purchase.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'purchase/:id',
        component: PurchaseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.purchase.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
