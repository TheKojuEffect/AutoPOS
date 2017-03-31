import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { PaginationUtil } from 'ng-jhipster';

import { VendorComponent } from './vendor.component';
import { VendorDetailComponent } from './vendor-detail.component';
import { VendorPopupComponent } from './vendor-dialog.component';
import { VendorDeletePopupComponent } from './vendor-delete-dialog.component';

@Injectable()
export class VendorResolvePagingParams implements Resolve<any> {

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

export const vendorRoutes: Routes = [
    {
        path: 'vendor',
        component: VendorComponent,
        resolve: {
            'pagingParams': VendorResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.vendor.home.title'
        }
    }, {
        path: 'vendor/:id',
        component: VendorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.vendor.home.title'
        }
    }
];

export const vendorPopupRoutes: Routes = [
    {
        path: 'vendor-new',
        component: VendorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.vendor.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'vendor/:id/edit',
        component: VendorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.vendor.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'vendor/:id/delete',
        component: VendorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.vendor.home.title'
        },
        outlet: 'popup'
    }
];
