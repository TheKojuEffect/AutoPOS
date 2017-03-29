import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { DatePipe } from '@angular/common';

import { CookieService } from 'angular2-cookie/services/cookies.service';
import {
    AccountService,
    AposLoginModalComponent,
    AuthServerProvider,
    AuthService,
    AutoPosSharedCommonModule,
    AutoPosSharedLibsModule,
    CSRFService,
    HasAnyAuthorityDirective,
    LoginModalService,
    LoginService,
    Principal,
    StateStorageService,
    UserService
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
export class AutoPosSharedModule {
}
