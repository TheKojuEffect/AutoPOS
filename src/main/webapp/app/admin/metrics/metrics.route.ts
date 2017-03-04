import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AposMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
  path: 'apos-metrics',
  component: AposMetricsMonitoringComponent,
  data: {
    pageTitle: 'metrics.title'
  }
};
