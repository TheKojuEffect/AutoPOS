import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { PurchaseLine } from './purchase-line.model';
import { PurchaseLineService } from './purchase-line.service';

@Component({
    selector: 'apos-purchase-line-detail',
    templateUrl: './purchase-line-detail.component.html'
})
export class PurchaseLineDetailComponent implements OnInit, OnDestroy {

    purchaseLine: PurchaseLine;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private purchaseLineService: PurchaseLineService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['purchaseLine']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.purchaseLineService.find(id).subscribe(purchaseLine => {
            this.purchaseLine = purchaseLine;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
