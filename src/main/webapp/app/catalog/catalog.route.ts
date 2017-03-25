import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { CatalogComponent } from './';

export const CATALOG_ROUTE: Route = {
  path: 'catalog',
  component: CatalogComponent,
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'autoPosApp.catalog.title'
  },
  canActivate: [UserRouteAccessService]
};
