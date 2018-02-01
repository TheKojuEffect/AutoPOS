import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage, LocalStorageService, SessionStorageService  } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';

import { SharedModule, UserRouteAccessService } from './shared';
import { HomeModule } from './home/home.module';
import { AdminModule } from './admin/admin.module';
import { AccountModule } from './account/account.module';

import { ActiveMenuDirective, AposMainComponent, ErrorComponent, FooterComponent, LayoutRoutingModule, NavbarComponent, PageRibbonComponent, ProfileService } from './layouts';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { CatalogModule } from './catalog/catalog.module';
import { AccountingModule } from './accounting/accounting.module';
import { TransactionModule } from './transaction';
import { PartyModule } from './party/party.module';
import { PurchaseModule } from './purchases/purchase.module';
import { SaleModule } from './sales/sale.module';
import { StockBookModule } from './stockbook';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({prefix: 'apos', separator: '-'}),
        SharedModule,
        HomeModule,
        AdminModule,
        AccountModule,
        CatalogModule,
        SaleModule,
        PurchaseModule,
        PartyModule,
        TransactionModule,
        AccountingModule,
        StockBookModule
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
        PaginationConfig,
        UserRouteAccessService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
            deps: [
                LocalStorageService,
                SessionStorageService
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [
                Injector
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [
                JhiEventManager
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [
                Injector
            ]
        }
    ],
    bootstrap: [AposMainComponent]
})
export class AutoPosAppModule {
}
