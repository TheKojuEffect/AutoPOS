import { Component } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

@Component({
    templateUrl: './catalog.component.html',
    styleUrls: [
        'catalog.scss'
    ]
})
export class CatalogComponent {

    constructor(private jhiLanguageService: JhiLanguageService) {
        this.jhiLanguageService.setLocations(['catalog']);
    }

}
