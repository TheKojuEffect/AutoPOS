import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';

import { PaginationUtil } from 'ng-jhipster';

import { DayBookEntryComponent } from './day-book-entry.component';
import { DayBookEntryDetailComponent } from './day-book-entry-detail.component';
import { DayBookEntryPopupComponent } from './day-book-entry-dialog.component';
import { DayBookEntryDeletePopupComponent } from './day-book-entry-delete-dialog.component';

@Injectable()
export class DayBookEntryResolvePagingParams implements Resolve<any> {

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

export const dayBookEntryRoutes: Routes = [
    {
        path: 'day-book-entry',
        component: DayBookEntryComponent,
        resolve: {
            'pagingParams': DayBookEntryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.dayBookEntry.home.title'
        }
    }, {
        path: 'day-book-entry/:id',
        component: DayBookEntryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.dayBookEntry.home.title'
        }
    }
];

export const dayBookEntryPopupRoutes: Routes = [
    {
        path: 'day-book-entry-new',
        component: DayBookEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.dayBookEntry.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'day-book-entry/:id/edit',
        component: DayBookEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.dayBookEntry.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'day-book-entry/:id/delete',
        component: DayBookEntryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.dayBookEntry.home.title'
        },
        outlet: 'popup'
    }
];
