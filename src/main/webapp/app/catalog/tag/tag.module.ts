import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { AutoPosSharedModule } from '../../shared';

import {
    TagComponent,
    TagDeleteDialogComponent,
    TagDeletePopupComponent,
    TagDetailComponent,
    TagDialogComponent,
    TagPopupComponent,
    TagPopupService,
    TagResolvePagingParams,
    TagRoutingModule,
    TagService
} from './';

@NgModule({
    imports: [
        AutoPosSharedModule,
        TagRoutingModule
    ],
    declarations: [
        TagComponent,
        TagDetailComponent,
        TagDialogComponent,
        TagDeleteDialogComponent,
        TagPopupComponent,
        TagDeletePopupComponent,
    ],
    entryComponents: [
        TagComponent,
        TagDialogComponent,
        TagPopupComponent,
        TagDeleteDialogComponent,
        TagDeletePopupComponent,
    ],
    providers: [
        TagService,
        TagPopupService,
        TagResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoPosTagModule {
}
