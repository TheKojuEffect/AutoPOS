import { Component } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

@Component({
    templateUrl: './accounting.component.html'
})
export class AccountingComponent {

    constructor(private jhiLanguageService: JhiLanguageService) {
        this.jhiLanguageService.setLocations(['accounting']);
    }

}
