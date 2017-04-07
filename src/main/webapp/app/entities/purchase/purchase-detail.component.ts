import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, JhiLanguageService } from 'ng-jhipster';
import { Purchase } from './purchase.model';
import { PurchaseService } from './purchase.service';

@Component({
    selector: 'apos-purchase-detail',
    templateUrl: './purchase-detail.component.html'
})
export class PurchaseDetailComponent implements OnInit, OnDestroy {

    purchase: Purchase;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(private eventManager: EventManager, private jhiLanguageService: JhiLanguageService,
                private purchaseService: PurchaseService,
                private route: ActivatedRoute) {
        this.jhiLanguageService.setLocations(['purchase']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInPurchases();
    }

    load(id) {
        this.purchaseService.find(id).subscribe(purchase => {
            this.purchase = purchase;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPurchases() {
        this.eventSubscriber = this.eventManager.subscribe('purchaseListModification', response => this.load(this.purchase.id));
    }

}
