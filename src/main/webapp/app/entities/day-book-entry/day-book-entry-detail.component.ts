import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { DayBookEntry } from './day-book-entry.model';
import { DayBookEntryService } from './day-book-entry.service';

@Component({
    selector: 'apos-day-book-entry-detail',
    templateUrl: './day-book-entry-detail.component.html'
})
export class DayBookEntryDetailComponent implements OnInit, OnDestroy {

    dayBookEntry: DayBookEntry;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private dayBookEntryService: DayBookEntryService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['dayBookEntry']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.dayBookEntryService.find(id).subscribe(dayBookEntry => {
            this.dayBookEntry = dayBookEntry;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
