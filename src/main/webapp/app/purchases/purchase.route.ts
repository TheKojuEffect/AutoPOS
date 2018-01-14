import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';
import { PurchaseDetailComponent } from './purchase-detail.component';
import { UserRouteAccessService } from '../shared';
import { PurchaseComponent } from './purchase.component';
import { PurchasesComponent } from './purchases.component';

@Injectable()
export class PurchaseResolvePagingParams implements Resolve<any> {

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

export const purchaseRoutes: Routes = [
    {
        path: 'purchase',
        component: PurchasesComponent,
        resolve: {
            'pagingParams': PurchaseResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.purchase.home.title'
        },
        canActivate: [UserRouteAccessService],
        children: [
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'normal'
            },
            {
                path: 'normal',
                component: PurchaseComponent,
                resolve: {
                    'pagingParams': PurchaseResolvePagingParams
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoPosApp.purchase.normal.title',
                    vat: false
                }
            },
            {
                path: 'vat',
                component: PurchaseComponent,
                resolve: {
                    'pagingParams': PurchaseResolvePagingParams
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoPosApp.purchase.vat.title',
                    vat: true
                }
            }
        ]
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
