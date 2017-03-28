import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { CatalogComponent } from './';
import { NgModule } from '@angular/core';
import { itemRoutes } from '../entities/item/item.route';

export const catalogRoutes: Routes = [
    {
        path: 'catalog',
        component: CatalogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.catalog.title'
        },
        canActivate: [UserRouteAccessService],
        children: [
            ...itemRoutes
        ]
    }
];


@NgModule({
    imports: [
        RouterModule.forChild(catalogRoutes)

    ],
    exports: [
        RouterModule
    ]
})
export class CatalogRoutingModule {
}
