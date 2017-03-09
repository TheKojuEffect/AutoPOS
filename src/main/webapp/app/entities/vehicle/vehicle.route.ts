import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { VehicleComponent } from './vehicle.component';
import { VehicleDetailComponent } from './vehicle-detail.component';
import { VehiclePopupComponent } from './vehicle-dialog.component';
import { VehicleDeletePopupComponent } from './vehicle-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class VehicleResolvePagingParams implements Resolve<any> {

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

export const vehicleRoute: Routes = [
  {
    path: 'vehicle',
    component: VehicleComponent,
    resolve: {
      'pagingParams': VehicleResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.vehicle.home.title'
    }
  }, {
    path: 'vehicle/:id',
    component: VehicleDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.vehicle.home.title'
    }
  }
];

export const vehiclePopupRoute: Routes = [
  {
    path: 'vehicle-new',
    component: VehiclePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.vehicle.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'vehicle/:id/edit',
    component: VehiclePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.vehicle.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'vehicle/:id/delete',
    component: VehicleDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'autoPosApp.vehicle.home.title'
    },
    outlet: 'popup'
  }
];
