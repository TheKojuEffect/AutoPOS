import { NgModule, Sanitizer } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { TranslateService } from 'ng2-translate';
import { AlertService } from 'ng-jhipster';
import {
    AutoPosSharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    AposAlertComponent,
    AposAlertErrorComponent
} from './';

export function alertServiceProvider(sanitizer: Sanitizer, translateService: TranslateService) {
    // set below to true to make alerts look like toast
    const isToast = false;
    return new AlertService(sanitizer, isToast, translateService);
}

@NgModule({
    imports: [
        AutoPosSharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        AposAlertComponent,
        AposAlertErrorComponent
    ],
    providers: [
        JhiLanguageHelper,
        {
            provide: AlertService,
            useFactory: alertServiceProvider,
            deps: [Sanitizer, TranslateService]
        },
        Title
    ],
    exports: [
        AutoPosSharedLibsModule,
        FindLanguageFromKeyPipe,
        AposAlertComponent,
        AposAlertErrorComponent
    ]
})
export class AutoPosSharedCommonModule {}
