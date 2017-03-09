import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PaymentComponent } from './payment.component';
import { PaymentDetailComponent } from './payment-detail.component';
import { PaymentPopupComponent } from './payment-dialog.component';
import { PaymentDeletePopupComponent } from './payment-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class PaymentResolvePagingParams implements Resolve<any> {

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

export const paymentRoute: Routes = [
  {
    path: 'payment',
    component: PaymentComponent,
    resolve: {
      'pagingParams': PaymentResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.payment.home.title'
    }
  }, {
    path: 'payment/:id',
    component: PaymentDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.payment.home.title'
    }
  }
];

export const paymentPopupRoute: Routes = [
  {
    path: 'payment-new',
    component: PaymentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.payment.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'payment/:id/edit',
    component: PaymentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.payment.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'payment/:id/delete',
    component: PaymentDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.payment.home.title'
    },
    outlet: 'popup'
  }
];
