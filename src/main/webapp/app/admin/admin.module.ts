import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

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
        LogsComponent,
        AposConfigurationComponent,
        AposHealthCheckComponent,
        AposHealthModalComponent,
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
export class AdminModule {}
