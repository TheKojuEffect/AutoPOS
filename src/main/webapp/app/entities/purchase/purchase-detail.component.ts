import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Purchase } from './purchase.model';
import { PurchaseService } from './purchase.service';

@Component({
    selector: 'apos-purchase-detail',
    templateUrl: './purchase-detail.component.html'
})
export class PurchaseDetailComponent implements OnInit, OnDestroy {

    purchase: Purchase;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private purchaseService: PurchaseService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['purchase']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.purchaseService.find(id).subscribe(purchase => {
            this.purchase = purchase;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
