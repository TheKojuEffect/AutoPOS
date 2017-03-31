import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { PaginationUtil } from 'ng-jhipster';

import { VehicleComponent } from './vehicle.component';
import { VehicleDetailComponent } from './vehicle-detail.component';
import { VehiclePopupComponent } from './vehicle-dialog.component';
import { VehicleDeletePopupComponent } from './vehicle-delete-dialog.component';

@Injectable()
export class VehicleResolvePagingParams implements Resolve<any> {

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

export const vehicleRoutes: Routes = [
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

export const vehiclePopupRoutes: Routes = [
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
