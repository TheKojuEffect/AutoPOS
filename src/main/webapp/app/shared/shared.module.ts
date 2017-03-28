import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import { CookieService } from 'angular2-cookie/services/cookies.service';
import {
    AutoPosSharedLibsModule,
    AutoPosSharedCommonModule,
    CSRFService,
    AuthService,
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
import { PaginationConfig } from '../blocks/config/uib-pagination.config';
import { customHttpProvider } from '../blocks/interceptor/http.provider';

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
        CookieService,
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        AuthService,
        UserService,
        DatePipe,
        customHttpProvider(),
        PaginationConfig
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
export class AutoPosSharedModule {}
