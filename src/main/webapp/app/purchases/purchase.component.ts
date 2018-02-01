import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { Purchase } from './purchase.model';
import { PurchaseService } from './purchase.service';
import { ITEMS_PER_PAGE, Principal } from '../shared';

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
    vat: boolean;

    constructor(private purchaseService: PurchaseService,
                private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
            this.vat = data['vat'];
        });
    }

    loadAll() {
        this.purchaseService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort(),
            vat: this.vat,
        }).subscribe(
            (res: HttpResponse<Purchase[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/purchase' + this.vat ? '/vat' : ''], {
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
        this.router.navigate(['/purchase' + this.vat ? '/vat' : '', {
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

    createNewPurchase(vat: boolean) {

        // .subscribe((itemResponse: HttpResponse<Item>) => {
        //         this.item = itemResponse.body;
        this.purchaseService.create(vat)
            .subscribe((purchaseResponse: HttpResponse<Purchase>) =>
                this.router.navigate(['/purchase', purchaseResponse.body.id]));
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
