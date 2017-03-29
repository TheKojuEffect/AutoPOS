import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../shared';

import { CatalogComponent } from './';
import { RoutingModule } from './catalog-routing.module';
import { ItemModule } from './item/item.module';
import { CategoryModule } from './category/category.module';
import { BrandModule } from './brand/brand.module';
import { TagModule } from './tag/tag.module';


@NgModule({
    imports: [
        SharedModule,
        ItemModule,
        CategoryModule,
        BrandModule,
        TagModule,
        RoutingModule
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
