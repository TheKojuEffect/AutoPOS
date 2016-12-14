import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { UpgradeModule } from '@angular/upgrade/static';
import { FormsModule } from '@angular/forms';
import { HttpModule, RequestOptions } from '@angular/http';
import { ItemService } from './catalog/item/item.service';
import { ItemSearchComponent } from './catalog/item/item-search.component';
import { CustomRequestOptions } from './services/auth/custom-request-options';
import { TypeaheadDirective, TypeaheadModule } from 'ng2-bootstrap';

@NgModule({
    imports: [
        BrowserModule,
        UpgradeModule,
        FormsModule,
        HttpModule,
        TypeaheadModule
    ],
    declarations: [
        ItemSearchComponent
    ],
    entryComponents: [
        ItemSearchComponent
    ],
    providers: [
        {provide: RequestOptions, useClass: CustomRequestOptions},
        ItemService
    ]
})
export class AppModule {
    ngDoBootstrap() {
    }
}
