import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { PriceHistory } from './price-history.model';
import { PriceHistoryPopupService } from './price-history-popup.service';
import { PriceHistoryService } from './price-history.service';

@Component({
    selector: 'apos-price-history-delete-dialog',
    templateUrl: './price-history-delete-dialog.component.html'
})
export class PriceHistoryDeleteDialogComponent {

    priceHistory: PriceHistory;

    constructor(private jhiLanguageService: JhiLanguageService,
                private priceHistoryService: PriceHistoryService,
                public activeModal: NgbActiveModal,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['priceHistory']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.priceHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'priceHistoryListModification',
                content: 'Deleted an priceHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'apos-price-history-delete-popup',
    template: ''
})
export class PriceHistoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private priceHistoryPopupService: PriceHistoryPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.priceHistoryPopupService
                .open(PriceHistoryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
