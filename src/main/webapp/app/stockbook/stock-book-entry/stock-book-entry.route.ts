import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { StockBookEntryComponent } from './stock-book-entry.component';
import { StockBookEntryDetailComponent } from './stock-book-entry-detail.component';
import { StockBookEntryPopupComponent } from './stock-book-entry-dialog.component';
import { StockBookEntryDeletePopupComponent } from './stock-book-entry-delete-dialog.component';

@Injectable()
export class StockBookEntryResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const stockBookEntryRoutes: Routes = [
    {
        path: 'stock-book-entry',
        component: StockBookEntryComponent,
        resolve: {
            'pagingParams': StockBookEntryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.stockBookEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'stock-book-entry/:id',
        component: StockBookEntryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.stockBookEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockBookEntryPopupRoutes: Routes = [
    {
        path: 'stock-book-entry-new',
        component: StockBookEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.stockBookEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stock-book-entry/:id/edit',
        component: StockBookEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.stockBookEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stock-book-entry/:id/delete',
        component: StockBookEntryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.stockBookEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
