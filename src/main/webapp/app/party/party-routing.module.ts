import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { PartyComponent } from './';
import { NgModule } from '@angular/core';
import { customerRoutes } from './customer';
import { vehicleRoutes } from './vehicle';
import { vendorRoutes } from './vendor';

export const partyRoutes: Routes = [
    {
        path: 'party',
        component: PartyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoPosApp.party.title'
        },
        canActivate: [UserRouteAccessService],
        children: [
            ...customerRoutes,
            ...vehicleRoutes,
            ...vendorRoutes
        ]
    }
];


@NgModule({
    imports: [
        RouterModule.forChild(partyRoutes)

    ],
    exports: [
        RouterModule
    ]
})
export class PartyRoutingModule {
}
