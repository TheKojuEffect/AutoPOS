import { Routes } from '@angular/router';

import { auditsRoute, userDialogRoute, userMgmtRoute } from './';

import { UserRouteAccessService } from '../shared';

const ADMIN_ROUTES = [
    auditsRoute,
    ...userMgmtRoute,
];

export const adminState: Routes = [{
    path: '',
    data: {
        authorities: ['ROLE_ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: ADMIN_ROUTES
},
    ...userDialogRoute
];
