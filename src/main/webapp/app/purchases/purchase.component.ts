import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { AlertService, EventManager, ParseLinks } from 'ng-jhipster';

import { Purchase } from './purchase.model';
import { PurchaseService } from './purchase.service';
import { Principal } from '../shared/index';
import { ITEMS_PER_PAGE } from '../shared/constants/pagination.constants';
import { ResponseWrapper } from '../shared/model/response-wrapper.model';

@Component({
    selector: 'apos-purchase',
    templateUrl: './purchase.component.html'
})
export class PurchaseComponent implements OnInit, OnDestroy {

    currentAccount: any;
    purchases: Purchase[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(private purchaseService: PurchaseService,
                private parseLinks: ParseLinks,
                private alertService: AlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: EventManager,) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    loadAll() {
        this.purchaseService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort(),
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/purchase'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/purchase', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPurchases();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Purchase) {
        return item.id;
    }

    registerChangeInPurchases() {
        this.eventSubscriber = this.eventManager.subscribe('purchaseListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    createNewPurchase() {
        this.purchaseService.create()
            .subscribe((purchase) => this.router.navigate(['/purchase', purchase.id]));
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.purchases = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
