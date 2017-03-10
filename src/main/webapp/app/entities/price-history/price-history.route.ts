import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PriceHistoryComponent } from './price-history.component';
import { PriceHistoryDetailComponent } from './price-history-detail.component';
import { PriceHistoryPopupComponent } from './price-history-dialog.component';
import { PriceHistoryDeletePopupComponent } from './price-history-delete-dialog.component';

import { Principal } from '../../shared';


export const priceHistoryRoute: Routes = [
  {
    path: 'price-history',
    component: PriceHistoryComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.priceHistory.home.title'
    }
  }, {
    path: 'price-history/:id',
    component: PriceHistoryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.priceHistory.home.title'
    }
  }
];

export const priceHistoryPopupRoute: Routes = [
  {
    path: 'price-history-new',
    component: PriceHistoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.priceHistory.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'price-history/:id/edit',
    component: PriceHistoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.priceHistory.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'price-history/:id/delete',
    component: PriceHistoryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.priceHistory.home.title'
    },
    outlet: 'popup'
  }
];
