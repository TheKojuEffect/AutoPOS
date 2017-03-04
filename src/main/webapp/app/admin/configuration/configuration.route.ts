import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AposConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
  path: 'apos-configuration',
  component: AposConfigurationComponent,
  data: {
    pageTitle: 'configuration.title'
  }
};
