import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import {
    CategoryComponent,
    CategoryDeleteDialogComponent,
    CategoryDeletePopupComponent,
    CategoryDetailComponent,
    CategoryDialogComponent,
    CategoryPopupComponent,
    CategoryPopupService,
    CategoryResolvePagingParams,
    CategoryRoutingModule,
    CategoryService
} from './';
import { SharedModule } from '../../shared';

@NgModule({
    imports: [
        SharedModule,
        CategoryRoutingModule
    ],
    declarations: [
        CategoryComponent,
        CategoryDetailComponent,
        CategoryDialogComponent,
        CategoryDeleteDialogComponent,
        CategoryPopupComponent,
        CategoryDeletePopupComponent,
    ],
    entryComponents: [
        CategoryComponent,
        CategoryDialogComponent,
        CategoryPopupComponent,
        CategoryDeleteDialogComponent,
        CategoryDeletePopupComponent,
    ],
    providers: [
        CategoryService,
        CategoryPopupService,
        CategoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CategoryModule {
}
