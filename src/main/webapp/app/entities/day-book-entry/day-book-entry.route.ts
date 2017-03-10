import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DayBookEntryComponent } from './day-book-entry.component';
import { DayBookEntryDetailComponent } from './day-book-entry-detail.component';
import { DayBookEntryPopupComponent } from './day-book-entry-dialog.component';
import { DayBookEntryDeletePopupComponent } from './day-book-entry-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class DayBookEntryResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

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

export const dayBookEntryRoute: Routes = [
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

export const dayBookEntryPopupRoute: Routes = [
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
