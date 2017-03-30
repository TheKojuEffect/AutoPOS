import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Payment } from './payment.model';
import { PaymentService } from './payment.service';

@Component({
    selector: 'apos-payment-detail',
    templateUrl: './payment-detail.component.html'
})
export class PaymentDetailComponent implements OnInit, OnDestroy {

    payment: Payment;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private paymentService: PaymentService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['payment']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.paymentService.find(id).subscribe(payment => {
            this.payment = payment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
