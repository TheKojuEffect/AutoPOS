import { Route } from '@angular/router';

import { AposMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
    path: 'apos-metrics',
    component: AposMetricsMonitoringComponent,
    data: {
        pageTitle: 'metrics.title'
    }
};
