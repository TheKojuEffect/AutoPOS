import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager, JhiLanguageService } from 'ng-jhipster';

import { PurchaseLine } from './purchase-line.model';
import { PurchaseLinePopupService } from './purchase-line-popup.service';
import { PurchaseLineService } from './purchase-line.service';
import { Purchase, PurchaseService } from '../purchase';
import { Item, ItemService } from '../../catalog/item';

@Component({
    selector: 'apos-purchase-line-dialog',
    templateUrl: './purchase-line-dialog.component.html'
})
export class PurchaseLineDialogComponent implements OnInit {

    purchaseLine: PurchaseLine;
    authorities: any[];
    isSaving: boolean;

    purchases: Purchase[];

    items: Item[];

    constructor(public activeModal: NgbActiveModal,
                private jhiLanguageService: JhiLanguageService,
                private alertService: AlertService,
                private purchaseLineService: PurchaseLineService,
                private purchaseService: PurchaseService,
                private itemService: ItemService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['purchaseLine']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.purchaseService.query().subscribe(
            (res: Response) => {
                this.purchases = res.json();
            }, (res: Response) => this.onError(res.json()));
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
        if (this.purchaseLine.id !== undefined) {
            this.purchaseLineService.update(this.purchaseLine)
                .subscribe((res: PurchaseLine) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.purchaseLineService.create(this.purchaseLine)
                .subscribe((res: PurchaseLine) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: PurchaseLine) {
        this.eventManager.broadcast({name: 'purchaseLineListModification', content: 'OK'});
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

    trackPurchaseById(index: number, item: Purchase) {
        return item.id;
    }

    trackItemById(index: number, item: Item) {
        return item.id;
    }
}

@Component({
    selector: 'apos-purchase-line-popup',
    template: ''
})
export class PurchaseLinePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private purchaseLinePopupService: PurchaseLinePopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.modalRef = this.purchaseLinePopupService
                    .open(PurchaseLineDialogComponent, params['id']);
            } else {
                this.modalRef = this.purchaseLinePopupService
                    .open(PurchaseLineDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
