import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    AutoPosSharedLibsModule,
    AutoPosSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    Principal,
    HasAnyAuthorityDirective,
    AposLoginModalComponent
} from './';

@NgModule({
    imports: [
        AutoPosSharedLibsModule,
        AutoPosSharedCommonModule
    ],
    declarations: [
        AposLoginModalComponent,
        HasAnyAuthorityDirective
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        DatePipe
    ],
    entryComponents: [AposLoginModalComponent],
    exports: [
        AutoPosSharedCommonModule,
        AposLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class SharedModule {}
