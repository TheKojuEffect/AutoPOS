import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager, JhiLanguageService } from 'ng-jhipster';

import { PriceHistory } from './price-history.model';
import { PriceHistoryPopupService } from './price-history-popup.service';
import { PriceHistoryService } from './price-history.service';
import { Item, ItemService } from '../item';

@Component({
    selector: 'apos-price-history-dialog',
    templateUrl: './price-history-dialog.component.html'
})
export class PriceHistoryDialogComponent implements OnInit {

    priceHistory: PriceHistory;
    authorities: any[];
    isSaving: boolean;

    items: Item[];

    constructor(public activeModal: NgbActiveModal,
                private jhiLanguageService: JhiLanguageService,
                private alertService: AlertService,
                private priceHistoryService: PriceHistoryService,
                private itemService: ItemService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['priceHistory']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.itemService.query().subscribe(
            (res: Response) => {
                this.items = res.json();
            }, (res: Response) => this.onError(res.json()));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.priceHistory.id !== undefined) {
            this.priceHistoryService.update(this.priceHistory)
                .subscribe((res: PriceHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.priceHistoryService.create(this.priceHistory)
                .subscribe((res: PriceHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: PriceHistory) {
        this.eventManager.broadcast({name: 'priceHistoryListModification', content: 'OK'});
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

    trackItemById(index: number, item: Item) {
        return item.id;
    }
}

@Component({
    selector: 'apos-price-history-popup',
    template: ''
})
export class PriceHistoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private priceHistoryPopupService: PriceHistoryPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.modalRef = this.priceHistoryPopupService
                    .open(PriceHistoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.priceHistoryPopupService
                    .open(PriceHistoryDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
