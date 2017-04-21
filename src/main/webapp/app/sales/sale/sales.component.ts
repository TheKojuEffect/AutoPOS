import { Component } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

@Component({
    templateUrl: './sales.component.html'
})
export class SalesComponent {

    constructor(private jhiLanguageService: JhiLanguageService) {
        this.jhiLanguageService.setLocations(['sales']);
    }

}
