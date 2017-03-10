import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { SaleLine } from './sale-line.model';
import { SaleLinePopupService } from './sale-line-popup.service';
import { SaleLineService } from './sale-line.service';
import { Sale, SaleService } from '../sale';
import { Item, ItemService } from '../item';
@Component({
    selector: 'apos-sale-line-dialog',
    templateUrl: './sale-line-dialog.component.html'
})
export class SaleLineDialogComponent implements OnInit {

    saleLine: SaleLine;
    authorities: any[];
    isSaving: boolean;

    sales: Sale[];

    items: Item[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private saleLineService: SaleLineService,
        private saleService: SaleService,
        private itemService: ItemService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['saleLine']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.saleService.query().subscribe(
            (res: Response) => { this.sales = res.json(); }, (res: Response) => this.onError(res.json()));
        this.itemService.query().subscribe(
            (res: Response) => { this.items = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.saleLine.id !== undefined) {
            this.saleLineService.update(this.saleLine)
                .subscribe((res: SaleLine) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.saleLineService.create(this.saleLine)
                .subscribe((res: SaleLine) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: SaleLine) {
        this.eventManager.broadcast({ name: 'saleLineListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackSaleById(index: number, item: Sale) {
        return item.id;
    }

    trackItemById(index: number, item: Item) {
        return item.id;
    }
}

@Component({
    selector: 'apos-sale-line-popup',
    template: ''
})
export class SaleLinePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private saleLinePopupService: SaleLinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.saleLinePopupService
                    .open(SaleLineDialogComponent, params['id']);
            } else {
                this.modalRef = this.saleLinePopupService
                    .open(SaleLineDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
