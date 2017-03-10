import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AposHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
  path: 'apos-health',
  component: AposHealthCheckComponent,
  data: {
    pageTitle: 'health.title'
  }
};
