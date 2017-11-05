import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { CatalogComponent } from './';
import { NgModule } from '@angular/core';
import { itemRoutes } from './item';
import { categoryRoutes } from './category';
import { brandRoutes } from './brand';
import { tagRoutes } from './tag';

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
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'item'
            },
            ...itemRoutes,
            ...categoryRoutes,
            ...brandRoutes,
            ...tagRoutes
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
