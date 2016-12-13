import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { UpgradeModule } from '@angular/upgrade/static';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

@NgModule({
    imports: [
        BrowserModule,
        UpgradeModule,
        FormsModule,
        HttpModule
    ]
})
export class AppModule {
    ngDoBootstrap() {
    }
}
