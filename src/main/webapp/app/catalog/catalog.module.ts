import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { AutoPosSharedModule } from '../shared';

import { CatalogComponent } from './';
import { CatalogRoutingModule } from './catalog-routing.module';
import { AutoPosItemModule } from './item/item.module';
import { AutoPosCategoryModule } from './category/category.module';
import { AutoPosBrandModule } from './brand/brand.module';
import { AutoPosTagModule } from './tag/tag.module';


@NgModule({
    imports: [
        AutoPosSharedModule,
        AutoPosItemModule,
        AutoPosCategoryModule,
        AutoPosBrandModule,
        AutoPosTagModule,
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
