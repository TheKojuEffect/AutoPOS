import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '../../shared';

import {
    VendorComponent,
    VendorDeleteDialogComponent,
    VendorDeletePopupComponent,
    VendorDetailComponent,
    VendorDialogComponent,
    VendorPopupComponent,
    VendorPopupService,
    VendorResolvePagingParams,
    VendorRoutingModule,
    VendorService,
} from './';

@NgModule({
    imports: [
        SharedModule,
        VendorRoutingModule
    ],
    declarations: [
        VendorComponent,
        VendorDetailComponent,
        VendorDialogComponent,
        VendorDeleteDialogComponent,
        VendorPopupComponent,
        VendorDeletePopupComponent,
    ],
    entryComponents: [
        VendorComponent,
        VendorDialogComponent,
        VendorPopupComponent,
        VendorDeleteDialogComponent,
        VendorDeletePopupComponent,
    ],
    providers: [
        VendorService,
        VendorPopupService,
        VendorResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VendorModule {
}
