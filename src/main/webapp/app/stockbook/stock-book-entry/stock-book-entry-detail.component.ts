import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { StockBookEntry } from './stock-book-entry.model';
import { StockBookEntryService } from './stock-book-entry.service';

@Component({
    selector: 'apos-stock-book-entry-detail',
    templateUrl: './stock-book-entry-detail.component.html'
})
export class StockBookEntryDetailComponent implements OnInit, OnDestroy {

    stockBookEntry: StockBookEntry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private stockBookEntryService: StockBookEntryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStockBookEntries();
    }

    load(id) {
        this.stockBookEntryService.find(id)
            .subscribe((stockBookEntryResponse: HttpResponse<StockBookEntry>) => {
                this.stockBookEntry = stockBookEntryResponse.body;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStockBookEntries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'stockBookEntryListModification',
            (response) => this.load(this.stockBookEntry.id)
        );
    }
}
