import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, JhiLanguageService } from 'ng-jhipster';
import { Sale } from './sale.model';
import { SaleService } from './sale.service';

@Component({
    selector: 'apos-sale-detail',
    templateUrl: './sale-detail.component.html'
})
export class SaleDetailComponent implements OnInit, OnDestroy {

    sale: Sale;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(private eventManager: EventManager, private jhiLanguageService: JhiLanguageService,
                private saleService: SaleService,
                private route: ActivatedRoute) {
        this.jhiLanguageService.setLocations(['sale', 'saleStatus']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInSales();
    }

    load(id) {
        this.saleService.find(id).subscribe(sale => {
            this.sale = sale;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSales() {
        this.eventSubscriber = this.eventManager.subscribe('saleListModification', response => this.load(this.sale.id));
    }

}
