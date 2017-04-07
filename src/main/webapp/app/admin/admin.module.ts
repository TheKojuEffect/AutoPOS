import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../shared';

import {
    adminState,
    AposConfigurationComponent,
    AposConfigurationService,
    AposDocsComponent,
    AposHealthCheckComponent,
    AposHealthModalComponent,
    AposHealthService,
    AposMetricsMonitoringComponent,
    AposMetricsMonitoringModalComponent,
    AposMetricsService,
    AuditsComponent,
    AuditsService,
    LogsComponent,
    LogsService,
    UserDeleteDialogComponent,
    UserDialogComponent,
    UserMgmtComponent,
    UserMgmtDeleteDialogComponent,
    UserMgmtDetailComponent,
    UserMgmtDialogComponent,
    UserModalService,
    UserResolve,
    UserResolvePagingParams
} from './';


@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(adminState, {useHash: true})
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
export class AdminModule {
}
