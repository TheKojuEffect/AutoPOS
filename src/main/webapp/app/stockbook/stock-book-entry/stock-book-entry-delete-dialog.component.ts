import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { StockBookEntry } from './stock-book-entry.model';
import { StockBookEntryPopupService } from './stock-book-entry-popup.service';
import { StockBookEntryService } from './stock-book-entry.service';

@Component({
    selector: 'apos-stock-book-entry-delete-dialog',
    templateUrl: './stock-book-entry-delete-dialog.component.html'
})
export class StockBookEntryDeleteDialogComponent {

    stockBookEntry: StockBookEntry;

    constructor(
        private stockBookEntryService: StockBookEntryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stockBookEntryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'stockBookEntryListModification',
                content: 'Deleted an stockBookEntry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'apos-stock-book-entry-delete-popup',
    template: ''
})
export class StockBookEntryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stockBookEntryPopupService: StockBookEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.stockBookEntryPopupService
                .open(StockBookEntryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
