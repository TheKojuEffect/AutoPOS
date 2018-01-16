import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { registerLocaleData } from '@angular/common';
import locale from '@angular/common/locales/en';

import {
    AutoPosSharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    AposAlertComponent,
    AposAlertErrorComponent
} from './';

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
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'en'
        },
    ],
    exports: [
        AutoPosSharedLibsModule,
        FindLanguageFromKeyPipe,
        AposAlertComponent,
        AposAlertErrorComponent
    ]
})
export class AutoPosSharedCommonModule {
    constructor() {
        registerLocaleData(locale);
    }
}
