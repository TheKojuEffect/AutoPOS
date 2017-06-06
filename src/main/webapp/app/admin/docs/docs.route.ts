import { Route } from '@angular/router';

import { AposDocsComponent } from './docs.component';

export const docsRoute: Route = {
    path: 'docs',
    component: AposDocsComponent,
    data: {
        pageTitle: 'global.menu.admin.apidocs'
    }
};
