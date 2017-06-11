import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Receipt } from './receipt.model';
import { ReceiptPopupService } from './receipt-popup.service';
import { ReceiptService } from './receipt.service';
import { Customer, CustomerService } from '../../party/customer';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'apos-receipt-dialog',
    templateUrl: './receipt-dialog.component.html'
})
export class ReceiptDialogComponent implements OnInit {

    receipt: Receipt;
    authorities: any[];
    isSaving: boolean;

    customers: Customer[];
    dateDp: any;

    constructor(public activeModal: NgbActiveModal,
                private alertService: AlertService,
                private receiptService: ReceiptService,
                private customerService: CustomerService,
                private eventManager: EventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.customerService.query()
            .subscribe((res: ResponseWrapper) => {
                this.customers = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.receipt.id !== undefined) {
            this.subscribeToSaveResponse(
                this.receiptService.update(this.receipt), false);
        } else {
            this.subscribeToSaveResponse(
                this.receiptService.create(this.receipt), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Receipt>, isCreated: boolean) {
        result.subscribe((res: Receipt) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Receipt, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'autoPosApp.receipt.created'
                : 'autoPosApp.receipt.updated',
            {param: result.id}, null);

        this.eventManager.broadcast({name: 'receiptListModification', content: 'OK'});
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

    trackCustomerById(index: number, item: Customer) {
        return item.id;
    }
}

@Component({
    selector: 'apos-receipt-popup',
    template: ''
})
export class ReceiptPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private receiptPopupService: ReceiptPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.modalRef = this.receiptPopupService
                    .open(ReceiptDialogComponent, params['id']);
            } else {
                this.modalRef = this.receiptPopupService
                    .open(ReceiptDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
