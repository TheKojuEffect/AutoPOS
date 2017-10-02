import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Receipt } from './receipt.model';
import { ReceiptService } from './receipt.service';

@Component({
    selector: 'apos-receipt-detail',
    templateUrl: './receipt-detail.component.html'
})
export class ReceiptDetailComponent implements OnInit, OnDestroy {

    receipt: Receipt;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager,
                private receiptService: ReceiptService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReceipts();
    }

    load(id) {
        this.receiptService.find(id).subscribe((receipt) => {
            this.receipt = receipt;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReceipts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'receiptListModification',
            (response) => this.load(this.receipt.id)
        );
    }
}
