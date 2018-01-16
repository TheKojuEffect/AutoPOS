import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PriceHistory } from './price-history.model';
import { PriceHistoryPopupService } from './price-history-popup.service';
import { PriceHistoryService } from './price-history.service';
import { Item, ItemService } from '../item';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'apos-price-history-dialog',
    templateUrl: './price-history-dialog.component.html'
})
export class PriceHistoryDialogComponent implements OnInit {

    priceHistory: PriceHistory;
    isSaving: boolean;

    items: Item[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private priceHistoryService: PriceHistoryService,
        private itemService: ItemService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.itemService.query()
            .subscribe((res: ResponseWrapper) => { this.items = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.priceHistory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.priceHistoryService.update(this.priceHistory));
        } else {
            this.subscribeToSaveResponse(
                this.priceHistoryService.create(this.priceHistory));
        }
    }

    private subscribeToSaveResponse(result: Observable<PriceHistory>) {
        result.subscribe((res: PriceHistory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PriceHistory) {
        this.eventManager.broadcast({ name: 'priceHistoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackItemById(index: number, item: Item) {
        return item.id;
    }
}

@Component({
    selector: 'apos-price-history-popup',
    template: ''
})
export class PriceHistoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private priceHistoryPopupService: PriceHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.priceHistoryPopupService
                    .open(PriceHistoryDialogComponent as Component, params['id']);
            } else {
                this.priceHistoryPopupService
                    .open(PriceHistoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
