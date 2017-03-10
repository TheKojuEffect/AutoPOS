import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PhoneComponent } from './phone.component';
import { PhoneDetailComponent } from './phone-detail.component';
import { PhonePopupComponent } from './phone-dialog.component';
import { PhoneDeletePopupComponent } from './phone-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class PhoneResolvePagingParams implements Resolve<any> {

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

export const phoneRoute: Routes = [
  {
    path: 'phone',
    component: PhoneComponent,
    resolve: {
      'pagingParams': PhoneResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.phone.home.title'
    }
  }, {
    path: 'phone/:id',
    component: PhoneDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.phone.home.title'
    }
  }
];

export const phonePopupRoute: Routes = [
  {
    path: 'phone-new',
    component: PhonePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.phone.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'phone/:id/edit',
    component: PhonePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.phone.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'phone/:id/delete',
    component: PhoneDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.phone.home.title'
    },
    outlet: 'popup'
  }
];
