import { Component } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

@Component({
    selector: 'apos-docs',
    templateUrl: './docs.component.html'
})
export class AposDocsComponent {
    constructor (
        private jhiLanguageService: JhiLanguageService
    ) {
        this.jhiLanguageService.setLocations(['global']);
    }
}
