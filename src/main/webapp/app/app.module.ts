import './vendor.ts';

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { AutoPosSharedModule, UserRouteAccessService } from './shared';
import { AutoPosHomeModule } from './home/home.module';
import { AutoPosAdminModule } from './admin/admin.module';
import { AutoPosAccountModule } from './account/account.module';
import { AutoPosEntityModule } from './entities/entity.module';

import { LayoutRoutingModule } from './layouts';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    AposMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';
import { CatalogModule } from './catalog/catalog.module';


@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        AutoPosSharedModule,
        AutoPosHomeModule,
        AutoPosAdminModule,
        AutoPosAccountModule,
        CatalogModule,
        AutoPosEntityModule
    ],
    declarations: [
        AposMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        { provide: Window, useValue: window },
        { provide: Document, useValue: document },
        UserRouteAccessService
    ],
    bootstrap: [ AposMainComponent ]
})
export class AutoPosAppModule {}
