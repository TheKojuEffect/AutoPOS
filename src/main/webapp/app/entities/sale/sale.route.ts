import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SaleComponent } from './sale.component';
import { SaleDetailComponent } from './sale-detail.component';
import { SalePopupComponent } from './sale-dialog.component';
import { SaleDeletePopupComponent } from './sale-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class SaleResolvePagingParams implements Resolve<any> {

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

export const saleRoute: Routes = [
  {
    path: 'sale',
    component: SaleComponent,
    resolve: {
      'pagingParams': SaleResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.sale.home.title'
    }
  }, {
    path: 'sale/:id',
    component: SaleDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.sale.home.title'
    }
  }
];

export const salePopupRoute: Routes = [
  {
    path: 'sale-new',
    component: SalePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.sale.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'sale/:id/edit',
    component: SalePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.sale.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'sale/:id/delete',
    component: SaleDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.sale.home.title'
    },
    outlet: 'popup'
  }
];
