import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PriceHistory } from './price-history.model';
import { PriceHistoryService } from './price-history.service';

@Component({
    selector: 'apos-price-history-detail',
    templateUrl: './price-history-detail.component.html'
})
export class PriceHistoryDetailComponent implements OnInit, OnDestroy {

    priceHistory: PriceHistory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private priceHistoryService: PriceHistoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPriceHistories();
    }

    load(id) {
        this.priceHistoryService.find(id).subscribe((priceHistory) => {
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

    registerChangeInPriceHistories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'priceHistoryListModification',
            (response) => this.load(this.priceHistory.id)
        );
    }
}
