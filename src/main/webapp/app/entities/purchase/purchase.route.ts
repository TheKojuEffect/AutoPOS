import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PurchaseComponent } from './purchase.component';
import { PurchaseDetailComponent } from './purchase-detail.component';
import { PurchasePopupComponent } from './purchase-dialog.component';
import { PurchaseDeletePopupComponent } from './purchase-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class PurchaseResolvePagingParams implements Resolve<any> {

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

export const purchaseRoute: Routes = [
  {
    path: 'purchase',
    component: PurchaseComponent,
    resolve: {
      'pagingParams': PurchaseResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.purchase.home.title'
    }
  }, {
    path: 'purchase/:id',
    component: PurchaseDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.purchase.home.title'
    }
  }
];

export const purchasePopupRoute: Routes = [
  {
    path: 'purchase-new',
    component: PurchasePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.purchase.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'purchase/:id/edit',
    component: PurchasePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.purchase.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'purchase/:id/delete',
    component: PurchaseDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.purchase.home.title'
    },
    outlet: 'popup'
  }
];
