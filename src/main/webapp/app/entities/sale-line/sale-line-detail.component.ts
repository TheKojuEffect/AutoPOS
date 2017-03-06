import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { SaleLine } from './sale-line.model';
import { SaleLineService } from './sale-line.service';

@Component({
    selector: 'apos-sale-line-detail',
    templateUrl: './sale-line-detail.component.html'
})
export class SaleLineDetailComponent implements OnInit, OnDestroy {

    saleLine: SaleLine;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private saleLineService: SaleLineService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['saleLine']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.saleLineService.find(id).subscribe(saleLine => {
            this.saleLine = saleLine;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
