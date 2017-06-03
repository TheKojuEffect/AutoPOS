import {Component} from '@angular/core';
import {JhiLanguageService} from 'ng-jhipster';
import {SaleService} from './sale.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
    templateUrl: './sales.component.html'
})
export class SalesComponent {

    constructor(private jhiLanguageService: JhiLanguageService,
                private saleService: SaleService,
                private router: Router) {

        this.jhiLanguageService.setLocations(['sales']);
    }

    createNewSale() {
        this.saleService.create()
            .subscribe((sale) => this.router.navigate(['/sale', sale.id]));
    }

}
