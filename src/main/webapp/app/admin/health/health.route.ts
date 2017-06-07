import { Route } from '@angular/router';

import { AposHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
    path: 'apos-health',
    component: AposHealthCheckComponent,
    data: {
        pageTitle: 'health.title'
    }
};
