import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../../shared';

import {

    SaleLineService
} from './';

@NgModule({
    imports: [
        SharedModule,
    ],
    declarations: [

    ],
    entryComponents: [

    ],
    providers: [
        SaleLineService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaleLineModule {
}
