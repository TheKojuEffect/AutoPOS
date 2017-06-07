import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Payment } from './payment.model';
import { PaymentPopupService } from './payment-popup.service';
import { PaymentService } from './payment.service';
import { Vendor, VendorService } from '../../party/vendor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'apos-payment-dialog',
    templateUrl: './payment-dialog.component.html'
})
export class PaymentDialogComponent implements OnInit {

    payment: Payment;
    authorities: any[];
    isSaving: boolean;

    vendors: Vendor[];
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private paymentService: PaymentService,
        private vendorService: VendorService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.vendorService.query()
            .subscribe((res: ResponseWrapper) => { this.vendors = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.payment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.paymentService.update(this.payment), false);
        } else {
            this.subscribeToSaveResponse(
                this.paymentService.create(this.payment), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Payment>, isCreated: boolean) {
        result.subscribe((res: Payment) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Payment, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'autoPosApp.payment.created'
                : 'autoPosApp.payment.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'paymentListModification', content: 'OK'});
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

    trackVendorById(index: number, item: Vendor) {
        return item.id;
    }
}

@Component({
    selector: 'apos-payment-popup',
    template: ''
})
export class PaymentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paymentPopupService: PaymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.paymentPopupService
                    .open(PaymentDialogComponent, params['id']);
            } else {
                this.modalRef = this.paymentPopupService
                    .open(PaymentDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
