import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { dayBookEntryPopupRoutes } from '.';

@NgModule({
    imports: [
        RouterModule.forChild(dayBookEntryPopupRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class DayBookEntryRoutingModule {
}
