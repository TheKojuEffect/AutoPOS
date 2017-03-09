import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutoPosSharedModule } from '../../shared';

import {
    BrandService,
    BrandPopupService,
    BrandComponent,
    BrandDetailComponent,
    BrandDialogComponent,
    BrandPopupComponent,
    BrandDeletePopupComponent,
    BrandDeleteDialogComponent,
    brandRoute,
    brandPopupRoute,
    BrandResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...brandRoute,
    ...brandPopupRoute,
];

@NgModule({
    imports: [
        AutoPosSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
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
export class AutoPosBrandModule {}
