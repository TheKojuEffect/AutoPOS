import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { PaginationUtil } from 'ng-jhipster';

import { TagComponent } from './tag.component';
import { TagDetailComponent } from './tag-detail.component';
import { TagPopupComponent } from './tag-dialog.component';
import { TagDeletePopupComponent } from './tag-delete-dialog.component';


@Injectable()
export class TagResolvePagingParams implements Resolve<any> {

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

export const tagRoutes: Routes = [
    {
        path: 'tag',
        component: TagComponent,
        resolve: {
            'pagingParams': TagResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.tag.home.title'
        }
    }, {
        path: 'tag/:id',
        component: TagDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.tag.home.title'
        }
    }
];

export const tagPopupRoutes: Routes = [
    {
        path: 'tag-new',
        component: TagPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.tag.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'tag/:id/edit',
        component: TagPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.tag.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'tag/:id/delete',
        component: TagDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.tag.home.title'
        },
        outlet: 'popup'
    }
];
