import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ParseLinks } from 'ng-jhipster';

import { SharedModule } from '../shared';

import {
    adminState,
    AuditsComponent,
    UserMgmtComponent,
    UserDialogComponent,
    UserDeleteDialogComponent,
    UserMgmtDetailComponent,
    UserMgmtDialogComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    AposMetricsMonitoringModalComponent,
    AposMetricsMonitoringComponent,
    AposHealthModalComponent,
    AposHealthCheckComponent,
    AposConfigurationComponent,
    AposDocsComponent,
    AuditsService,
    AposConfigurationService,
    AposHealthService,
    AposMetricsService,
    LogsService,
    UserResolvePagingParams,
    UserResolve,
    UserModalService
} from './';


@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(adminState, { useHash: true })
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        UserDialogComponent,
        UserDeleteDialogComponent,
        UserMgmtDetailComponent,
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        LogsComponent,
        AposConfigurationComponent,
        AposHealthCheckComponent,
        AposHealthModalComponent,
        AposDocsComponent,
        AposMetricsMonitoringComponent,
        AposMetricsMonitoringModalComponent
    ],
    entryComponents: [
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        AposHealthModalComponent,
        AposMetricsMonitoringModalComponent,
    ],
    providers: [
        AuditsService,
        AposConfigurationService,
        AposHealthService,
        AposMetricsService,
        LogsService,
        UserResolvePagingParams,
        UserResolve,
        UserModalService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoPosAdminModule {}
