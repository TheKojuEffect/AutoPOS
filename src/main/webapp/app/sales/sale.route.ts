import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SaleComponent } from './sale.component';
import { SaleDetailComponent } from './sale-detail.component';
import { UserRouteAccessService } from '../shared/auth/user-route-access-service';
import { SaleStatus } from './sale.model';
import { SalesComponent } from './sales.component';

@Injectable()
export class SaleResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'lastModifiedDate,desc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

export const saleRoutes: Routes = [
    {
        path: 'sale',
        component: SalesComponent,
        resolve: {
            'pagingParams': SaleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.sale.home.title'
        },
        canActivate: [UserRouteAccessService],
        children: [
            {
                path: 'pending',
                component: SaleComponent,
                resolve: {
                    'pagingParams': SaleResolvePagingParams
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoPosApp.sale.pending.title',
                    status: SaleStatus.PENDING
                }
            },
            {
                path: 'completed',
                component: SaleComponent,
                resolve: {
                    'pagingParams': SaleResolvePagingParams
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoPosApp.sale.completed.title',
                    status: SaleStatus.COMPLETED
                }
            }
        ]
    }, {
        path: 'sale/:id',
        component: SaleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.sale.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
