import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';

import { PaginationUtil } from 'ng-jhipster';

import { BrandComponent } from './brand.component';
import { BrandDetailComponent } from './brand-detail.component';
import { BrandPopupComponent } from './brand-dialog.component';
import { BrandDeletePopupComponent } from './brand-delete-dialog.component';

@Injectable()
export class BrandResolvePagingParams implements Resolve<any> {

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

export const brandRoutes: Routes = [
    {
        path: 'brand',
        component: BrandComponent,
        resolve: {
            'pagingParams': BrandResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.brand.home.title'
        }
    }, {
        path: 'brand/:id',
        component: BrandDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.brand.home.title'
        }
    }
];

export const brandPopupRoutes: Routes = [
    {
        path: 'brand-new',
        component: BrandPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.brand.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'brand/:id/edit',
        component: BrandPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.brand.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'brand/:id/delete',
        component: BrandDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.brand.home.title'
        },
        outlet: 'popup'
    }
];