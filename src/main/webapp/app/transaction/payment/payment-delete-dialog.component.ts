import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Payment } from './payment.model';
import { PaymentPopupService } from './payment-popup.service';
import { PaymentService } from './payment.service';

@Component({
    selector: 'apos-payment-delete-dialog',
    templateUrl: './payment-delete-dialog.component.html'
})
export class PaymentDeleteDialogComponent {

    payment: Payment;

    constructor(
        private paymentService: PaymentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paymentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'paymentListModification',
                content: 'Deleted an payment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'apos-payment-delete-popup',
    template: ''
})
export class PaymentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paymentPopupService: PaymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.paymentPopupService
                .open(PaymentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
