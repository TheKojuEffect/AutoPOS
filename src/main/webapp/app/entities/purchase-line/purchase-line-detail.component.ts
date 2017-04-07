import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, JhiLanguageService } from 'ng-jhipster';
import { PurchaseLine } from './purchase-line.model';
import { PurchaseLineService } from './purchase-line.service';

@Component({
    selector: 'apos-purchase-line-detail',
    templateUrl: './purchase-line-detail.component.html'
})
export class PurchaseLineDetailComponent implements OnInit, OnDestroy {

    purchaseLine: PurchaseLine;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(private eventManager: EventManager, private jhiLanguageService: JhiLanguageService,
                private purchaseLineService: PurchaseLineService,
                private route: ActivatedRoute) {
        this.jhiLanguageService.setLocations(['purchaseLine']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInPurchaseLines();
    }

    load(id) {
        this.purchaseLineService.find(id).subscribe(purchaseLine => {
            this.purchaseLine = purchaseLine;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPurchaseLines() {
        this.eventSubscriber = this.eventManager.subscribe('purchaseLineListModification', response => this.load(this.purchaseLine.id));
    }

}
