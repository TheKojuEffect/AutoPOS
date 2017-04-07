import { Route } from '@angular/router';
import { AposConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
    path: 'apos-configuration',
    component: AposConfigurationComponent,
    data: {
        pageTitle: 'configuration.title'
    }
};
