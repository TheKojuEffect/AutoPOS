import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { PaginationUtil } from 'ng-jhipster';

import { SaleLineComponent } from './sale-line.component';
import { SaleLineDetailComponent } from './sale-line-detail.component';
import { SaleLinePopupComponent } from './sale-line-dialog.component';
import { SaleLineDeletePopupComponent } from './sale-line-delete-dialog.component';

@Injectable()
export class SaleLineResolvePagingParams implements Resolve<any> {

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

export const saleLineRoute: Routes = [
    {
        path: 'sale-line',
        component: SaleLineComponent,
        resolve: {
            'pagingParams': SaleLineResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.saleLine.home.title'
        }
    }, {
        path: 'sale-line/:id',
        component: SaleLineDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.saleLine.home.title'
        }
    }
];

export const saleLinePopupRoute: Routes = [
    {
        path: 'sale-line-new',
        component: SaleLinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.saleLine.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'sale-line/:id/edit',
        component: SaleLinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.saleLine.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'sale-line/:id/delete',
        component: SaleLineDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.saleLine.home.title'
        },
        outlet: 'popup'
    }
];
