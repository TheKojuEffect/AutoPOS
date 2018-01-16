import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../shared';

import {
    adminState, AuditsComponent, AuditsService, UserDeleteDialogComponent, UserDialogComponent, UserMgmtComponent, UserMgmtDeleteDialogComponent, UserMgmtDetailComponent,
    UserMgmtDialogComponent, UserModalService, UserResolve, UserResolvePagingParams
} from './';

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(adminState),
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        UserDialogComponent,
        UserDeleteDialogComponent,
        UserMgmtDetailComponent,
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
    ],
    entryComponents: [
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
    ],
    providers: [
        AuditsService,
        UserResolvePagingParams,
        UserResolve,
        UserModalService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminModule {}
