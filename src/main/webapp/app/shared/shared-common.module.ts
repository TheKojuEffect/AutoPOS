import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';

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
export class AutoPosSharedCommonModule {}
