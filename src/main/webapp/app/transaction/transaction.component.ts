import { Component } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

@Component({
    templateUrl: './transaction.component.html'
})
export class TransactionComponent {

    constructor(private jhiLanguageService: JhiLanguageService) {
        this.jhiLanguageService.setLocations(['transaction']);
    }

}
