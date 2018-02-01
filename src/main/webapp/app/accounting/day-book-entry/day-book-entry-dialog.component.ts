import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DayBookEntry } from './day-book-entry.model';
import { DayBookEntryPopupService } from './day-book-entry-popup.service';
import { DayBookEntryService } from './day-book-entry.service';

@Component({
    selector: 'apos-day-book-entry-dialog',
    templateUrl: './day-book-entry-dialog.component.html'
})
export class DayBookEntryDialogComponent implements OnInit {

    dayBookEntry: DayBookEntry;
    isSaving: boolean;
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dayBookEntryService: DayBookEntryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dayBookEntry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dayBookEntryService.update(this.dayBookEntry));
        } else {
            this.subscribeToSaveResponse(
                this.dayBookEntryService.create(this.dayBookEntry));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DayBookEntry>>) {
        result.subscribe((res: HttpResponse<DayBookEntry>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DayBookEntry) {
        this.eventManager.broadcast({ name: 'dayBookEntryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'apos-day-book-entry-popup',
    template: ''
})
export class DayBookEntryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dayBookEntryPopupService: DayBookEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dayBookEntryPopupService
                    .open(DayBookEntryDialogComponent as Component, params['id']);
            } else {
                this.dayBookEntryPopupService
                    .open(DayBookEntryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
