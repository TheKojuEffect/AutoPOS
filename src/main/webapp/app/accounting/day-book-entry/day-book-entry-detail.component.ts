import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { DayBookEntry } from './day-book-entry.model';
import { DayBookEntryService } from './day-book-entry.service';

@Component({
    selector: 'apos-day-book-entry-detail',
    templateUrl: './day-book-entry-detail.component.html'
})
export class DayBookEntryDetailComponent implements OnInit, OnDestroy {

    dayBookEntry: DayBookEntry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private dayBookEntryService: DayBookEntryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDayBookEntries();
    }

    load(id) {
        this.dayBookEntryService.find(id).subscribe((dayBookEntry) => {
            this.dayBookEntry = dayBookEntry;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDayBookEntries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dayBookEntryListModification',
            (response) => this.load(this.dayBookEntry.id)
        );
    }
}
