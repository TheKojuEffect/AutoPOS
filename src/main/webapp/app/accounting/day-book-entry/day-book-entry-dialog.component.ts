import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { DayBookEntry } from './day-book-entry.model';
import { DayBookEntryPopupService } from './day-book-entry-popup.service';
import { DayBookEntryService } from './day-book-entry.service';

@Component({
    selector: 'apos-day-book-entry-dialog',
    templateUrl: './day-book-entry-dialog.component.html'
})
export class DayBookEntryDialogComponent implements OnInit {

    dayBookEntry: DayBookEntry;
    authorities: any[];
    isSaving: boolean;
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private dayBookEntryService: DayBookEntryService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dayBookEntry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dayBookEntryService.update(this.dayBookEntry), false);
        } else {
            this.subscribeToSaveResponse(
                this.dayBookEntryService.create(this.dayBookEntry), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<DayBookEntry>, isCreated: boolean) {
        result.subscribe((res: DayBookEntry) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DayBookEntry, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'autoPosApp.dayBookEntry.created'
                : 'autoPosApp.dayBookEntry.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'dayBookEntryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'apos-day-book-entry-popup',
    template: ''
})
export class DayBookEntryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dayBookEntryPopupService: DayBookEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.dayBookEntryPopupService
                    .open(DayBookEntryDialogComponent, params['id']);
            } else {
                this.modalRef = this.dayBookEntryPopupService
                    .open(DayBookEntryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
