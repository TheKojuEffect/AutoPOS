import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DayBookEntry } from './day-book-entry.model';
import { DayBookEntryPopupService } from './day-book-entry-popup.service';
import { DayBookEntryService } from './day-book-entry.service';

@Component({
    selector: 'apos-day-book-entry-delete-dialog',
    templateUrl: './day-book-entry-delete-dialog.component.html'
})
export class DayBookEntryDeleteDialogComponent {

    dayBookEntry: DayBookEntry;

    constructor(
        private dayBookEntryService: DayBookEntryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dayBookEntryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dayBookEntryListModification',
                content: 'Deleted an dayBookEntry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'apos-day-book-entry-delete-popup',
    template: ''
})
export class DayBookEntryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dayBookEntryPopupService: DayBookEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dayBookEntryPopupService
                .open(DayBookEntryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
