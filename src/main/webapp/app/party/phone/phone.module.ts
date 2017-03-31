import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';

import {
    PhoneService,
    PhonePopupService,
    PhoneComponent,
    PhoneDetailComponent,
    PhoneDialogComponent,
    PhonePopupComponent,
    PhoneDeletePopupComponent,
    PhoneDeleteDialogComponent,
    phoneRoute,
    phonePopupRoute,
    PhoneResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...phoneRoute,
    ...phonePopupRoute,
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PhoneComponent,
        PhoneDetailComponent,
        PhoneDialogComponent,
        PhoneDeleteDialogComponent,
        PhonePopupComponent,
        PhoneDeletePopupComponent,
    ],
    entryComponents: [
        PhoneComponent,
        PhoneDialogComponent,
        PhonePopupComponent,
        PhoneDeleteDialogComponent,
        PhoneDeletePopupComponent,
    ],
    providers: [
        PhoneService,
        PhonePopupService,
        PhoneResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PhoneModule {}
