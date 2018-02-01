import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DEBUG_INFO_ENABLED } from '../app.constants';


import { navbarRoute } from '../app.route';
import { errorRoute } from './';

const LAYOUT_ROUTES = [
    navbarRoute,
    ...errorRoute
];

@NgModule({
    imports: [
        RouterModule.forRoot(LAYOUT_ROUTES, {useHash: true, enableTracing: DEBUG_INFO_ENABLED})
    ],
    exports: [
        RouterModule
    ]
})
export class LayoutRoutingModule {
}
