import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { DayBookEntry } from './day-book-entry.model';
import { DayBookEntryPopupService } from './day-book-entry-popup.service';
import { DayBookEntryService } from './day-book-entry.service';

@Component({
    selector: 'apos-day-book-entry-delete-dialog',
    templateUrl: './day-book-entry-delete-dialog.component.html'
})
export class DayBookEntryDeleteDialogComponent {

    dayBookEntry: DayBookEntry;

    constructor(private jhiLanguageService: JhiLanguageService,
                private dayBookEntryService: DayBookEntryService,
                public activeModal: NgbActiveModal,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['dayBookEntry']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dayBookEntryService.delete(id).subscribe(response => {
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

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private dayBookEntryPopupService: DayBookEntryPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.dayBookEntryPopupService
                .open(DayBookEntryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
