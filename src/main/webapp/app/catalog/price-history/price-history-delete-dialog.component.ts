import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PriceHistory } from './price-history.model';
import { PriceHistoryPopupService } from './price-history-popup.service';
import { PriceHistoryService } from './price-history.service';

@Component({
    selector: 'apos-price-history-delete-dialog',
    templateUrl: './price-history-delete-dialog.component.html'
})
export class PriceHistoryDeleteDialogComponent {

    priceHistory: PriceHistory;

    constructor(
        private priceHistoryService: PriceHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.priceHistoryService.delete(id).subscribe((response) => {
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

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private priceHistoryPopupService: PriceHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.priceHistoryPopupService
                .open(PriceHistoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
