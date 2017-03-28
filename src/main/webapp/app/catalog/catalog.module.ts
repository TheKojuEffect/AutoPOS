import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { AutoPosSharedModule } from '../shared';

import { CatalogComponent } from './';
import { CatalogRoutingModule } from './catalog-routing.module';
import { AutoPosItemModule } from '../entities/item/item.module';


@NgModule({
    imports: [
        AutoPosSharedModule,
        AutoPosItemModule,
        CatalogRoutingModule
    ],
    declarations: [
        CatalogComponent,
    ],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CatalogModule {
}
