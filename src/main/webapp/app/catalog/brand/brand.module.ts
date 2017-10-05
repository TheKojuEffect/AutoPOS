import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../../shared';

import {
    BrandComponent,
    BrandDeleteDialogComponent,
    BrandDeletePopupComponent,
    BrandDetailComponent,
    BrandDialogComponent,
    BrandPopupComponent,
    BrandPopupService,
    BrandResolvePagingParams,
    BrandRoutingModule,
    BrandService,
} from './';

@NgModule({
    imports: [
        SharedModule,
        BrandRoutingModule
    ],
    declarations: [
        BrandComponent,
        BrandDetailComponent,
        BrandDialogComponent,
        BrandDeleteDialogComponent,
        BrandPopupComponent,
        BrandDeletePopupComponent,
    ],
    entryComponents: [
        BrandComponent,
        BrandDialogComponent,
        BrandPopupComponent,
        BrandDeleteDialogComponent,
        BrandDeletePopupComponent,
    ],
    providers: [
        BrandService,
        BrandPopupService,
        BrandResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BrandModule {
}
