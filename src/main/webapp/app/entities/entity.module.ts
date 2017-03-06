import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AutoPosBrandModule } from './brand/brand.module';
import { AutoPosCategoryModule } from './category/category.module';
import { AutoPosTagModule } from './tag/tag.module';
import { AutoPosItemModule } from './item/item.module';
import { AutoPosPriceHistoryModule } from './price-history/price-history.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AutoPosBrandModule,
        AutoPosCategoryModule,
        AutoPosTagModule,
        AutoPosItemModule,
        AutoPosPriceHistoryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoPosEntityModule {}
