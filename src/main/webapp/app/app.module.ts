import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { SharedModule, UserRouteAccessService } from './shared';
import { AutoPosHomeModule } from './home/home.module';
import { AutoPosAdminModule } from './admin/admin.module';
import { AutoPosAccountModule } from './account/account.module';
import { AutoPosEntityModule } from './entities/entity.module';

import {
    ActiveMenuDirective,
    AposMainComponent,
    ErrorComponent,
    FooterComponent,
    LayoutRoutingModule,
    NavbarComponent,
    PageRibbonComponent,
    ProfileService
} from './layouts';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { CatalogModule } from './catalog/catalog.module';
import { AccountingModule } from './accounting/accounting.module';
import { TransactionModule } from './transaction/transaction.module';
import { PartyModule } from './party/party.module';


@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({prefix: 'jhi', separator: '-'}),
        SharedModule,
        AutoPosHomeModule,
        AutoPosAdminModule,
        AutoPosAccountModule,
        CatalogModule,
        PartyModule,
        TransactionModule,
        AccountingModule,
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
        {provide: Window, useValue: window},
        {provide: Document, useValue: document},
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [AposMainComponent]
})
export class AutoPosAppModule {
}
