import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { SharedModule, UserRouteAccessService } from './shared';
import { HomeModule } from './home/home.module';
import { AdminModule } from './admin/admin.module';
import { AccountModule } from './account/account.module';

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
import { PurchaseModule } from './purchases/purchase/purchase.module';
import { SaleModule } from './sales/sale.module';
import { PurchaseLineModule } from './purchases/purchase-line/purchase-line.module';
import moment = require('moment');


@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({prefix: 'jhi', separator: '-'}),
        SharedModule,
        HomeModule,
        AdminModule,
        AccountModule,
        CatalogModule,
        SaleModule,
        PurchaseModule,
        PurchaseLineModule,
        PartyModule,
        TransactionModule,
        AccountingModule
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
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [AposMainComponent]
})
export class AutoPosAppModule {

    constructor() {
        Date.prototype.toJSON = function () {
            return moment(this).format('YYYY-MM-DDTHH:mm:ss');
        };
    }

}
