import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, JhiLanguageService } from 'ng-jhipster';
import { SaleLine } from './sale-line.model';
import { SaleLineService } from './sale-line.service';

@Component({
    selector: 'apos-sale-line-detail',
    templateUrl: './sale-line-detail.component.html'
})
export class SaleLineDetailComponent implements OnInit, OnDestroy {

    saleLine: SaleLine;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(private eventManager: EventManager, private jhiLanguageService: JhiLanguageService,
                private saleLineService: SaleLineService,
                private route: ActivatedRoute) {
        this.jhiLanguageService.setLocations(['saleLine']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInSaleLines();
    }

    load(id) {
        this.saleLineService.find(id).subscribe(saleLine => {
            this.saleLine = saleLine;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSaleLines() {
        this.eventSubscriber = this.eventManager.subscribe('saleLineListModification', response => this.load(this.saleLine.id));
    }

}
