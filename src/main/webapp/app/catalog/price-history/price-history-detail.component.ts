import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, JhiLanguageService } from 'ng-jhipster';
import { PriceHistory } from './price-history.model';
import { PriceHistoryService } from './price-history.service';

@Component({
    selector: 'apos-price-history-detail',
    templateUrl: './price-history-detail.component.html'
})
export class PriceHistoryDetailComponent implements OnInit, OnDestroy {

    priceHistory: PriceHistory;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(private eventManager: EventManager, private jhiLanguageService: JhiLanguageService,
                private priceHistoryService: PriceHistoryService,
                private route: ActivatedRoute) {
        this.jhiLanguageService.setLocations(['priceHistory']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInPriceHistorys();
    }

    load(id) {
        this.priceHistoryService.find(id).subscribe(priceHistory => {
            this.priceHistory = priceHistory;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPriceHistorys() {
        this.eventSubscriber = this.eventManager.subscribe('priceHistoryListModification', response => this.load(this.priceHistory.id));
    }

}
