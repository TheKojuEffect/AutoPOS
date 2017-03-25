import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutoPosSharedModule } from '../shared';

import { CATALOG_ROUTE, CatalogComponent } from './';


@NgModule({
    imports: [
        AutoPosSharedModule,
        RouterModule.forRoot([ CATALOG_ROUTE ], { useHash: true })
    ],
    declarations: [
        CatalogComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoPosCatalogModule {}
