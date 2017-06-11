import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, JhiLanguageService } from 'ng-jhipster';
import { Purchase } from './purchase.model';
import { PurchaseService } from './purchase.service';
import { ItemService } from '../catalog/item/item.service';
import { Item } from '../catalog/item/item.model';
import { Observable } from 'rxjs/Observable';
import { PurchaseLine } from './purchase-line.model';
import { PurchaseLineService } from './purchase-line.service';

import * as _ from 'lodash';
import { Vendor } from '../party/vendor/vendor.model';
import { VendorService } from '../party/vendor/vendor.service';

@Component({
    selector: 'apos-purchase-detail',
    templateUrl: './purchase-detail.component.html',
    providers: [
        ItemService
    ]
})
export class PurchaseDetailComponent implements OnInit, OnDestroy {

    purchase: Purchase;
    private subscription: any;
    private eventSubscriber: Subscription;
    searching = false;
    noItems = false;
    searchingVendor = false;
    searchFailed = false;
    searchVendorFailed = false;
    line = new PurchaseLine();

    constructor(private eventManager: EventManager,
                private jhiLanguageService: JhiLanguageService,
                private purchaseService: PurchaseService,
                private purchaseLineService: PurchaseLineService,
                private itemService: ItemService,
                private vendorService: VendorService,
                private router: Router,
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

    gotoPurchaseList() {
        this.router.navigate(['./purchase']);
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPurchases() {
        this.eventSubscriber = this.eventManager.subscribe('purchaseListModification', response => this.load(this.purchase.id));
    }

    search = (text: Observable<string>) =>
        text.debounceTime(200)
            .distinctUntilChanged()
            .do(() => {
                this.searching = true;
                this.noItems = false;
            })
            .switchMap(term =>
                this.itemService.search(term)
                    .do(
                        (items) => {
                            this.searchFailed = false;
                            if (items.length === 0) {
                                this.noItems = true;
                            }
                        }
                    )
                    .catch(() => {
                        this.searchFailed = true;
                        return Observable.of([]);
                    }))
            .do(() => this.searching = false);

    itemFormatter = (item: Item) => item.name;

    searchVendor = (text: Observable<string>) =>
        text.debounceTime(200)
            .distinctUntilChanged()
            .do(() => this.searchingVendor = true)
            .switchMap(term =>
                this.vendorService.search(term)
                    .do(() => this.searchVendorFailed = false)
                    .catch(() => {
                        this.searchVendorFailed = true;
                        return Observable.of([]);
                    }))
            .do(() => this.searchingVendor = false);

    vendorFormatter = (vendor: Vendor) => vendor.name;

    onLineItemSubmit() {
        this.line.purchase = this.purchase;
        if (this.line.id) {

            const lineIndex = _.findIndex(this.purchase.lines,
                line => line.id === this.line.id);

            this.purchaseLineService.update(
                this.purchase.id,
                this.line.id,
                this.line
            ).subscribe((line) => this.purchase.lines.splice(lineIndex, 1, line));

        } else {
            this.purchaseLineService.create(
                this.purchase.id,
                this.line
            ).subscribe((line) => this.purchase.lines.push(line));
        }
        this.resetLineItem();
    };

    resetLineItem() {
        this.line = new PurchaseLine();
    };

    editPurchaseLine(line: PurchaseLine) {
        this.line = Object.assign({}, line);
    };

    get subTotal(): number {
        return _.sumBy(this.purchase.lines, 'amount');
    }

    get total(): number {
        return this.subTotal - this.purchase.discount;
    }

    get hasLines(): boolean {
        return this.purchase.lines && this.purchase.lines.length > 0;
    }

    deletePurchase() {
        const sure = window.confirm('Are you sure you want to delete this purchase?');
        if (sure) {
            this.purchaseService.delete(this.purchase.id).subscribe(() => {
                this.gotoPurchaseList();
            });
        }
    }

    updatePurchase() {
        this.purchaseService.update(this.purchase)
            .subscribe(() => this.gotoPurchaseList());
    }

    deletePurchaseLine() {
        const sure = window.confirm('Are you sure you want to delete this purchase item?');
        if (sure) {
            this.purchaseLineService.delete(this.purchase.id, this.line.id)
                .subscribe(() => {
                    const lineIndex = _.findIndex(this.purchase.lines,
                        line => line.id === this.line.id);
                    this.purchase.lines.splice(lineIndex, 1);
                    this.resetLineItem();
                });
        }
    }

}
