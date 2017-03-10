import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Sale } from './sale.model';
import { SaleService } from './sale.service';

@Component({
    selector: 'apos-sale-detail',
    templateUrl: './sale-detail.component.html'
})
export class SaleDetailComponent implements OnInit, OnDestroy {

    sale: Sale;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private saleService: SaleService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['sale', 'saleStatus']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.saleService.find(id).subscribe(sale => {
            this.sale = sale;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
