import { Component } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

@Component({
    templateUrl: './catalog.component.html'
})
export class CatalogComponent {

    constructor(private jhiLanguageService: JhiLanguageService) {
        this.jhiLanguageService.setLocations(['catalog']);
    }

}
