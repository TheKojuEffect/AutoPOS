import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Sale, SaleStatus } from './sale.model';
import { SaleService } from './sale.service';
import { ITEMS_PER_PAGE, Principal } from '../shared';

@Component({
    selector: 'apos-sale',
    templateUrl: './sale.component.html'
})
export class SaleComponent implements OnInit, OnDestroy {

    currentAccount: any;
    sales: Sale[];
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
    saleStatus: SaleStatus;
    vat: boolean;

    constructor(private saleService: SaleService,
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
            this.saleStatus = data['status'];
            this.vat = data['vat'] || false;
        });
    }

    loadAll() {
        this.saleService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort(),
            status: this.saleStatus,
            vat: this.vat
        }).subscribe(
            (res: HttpResponse<Sale[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    createNewSale(vat) {
        this.saleService.create(vat)
            .subscribe((saleResponse) => this.router.navigate(['/sale', saleResponse.body.id]));
    }

    transition() {
        const status = this.saleStatus.toString().toLowerCase();
        this.router.navigate([`/sale/${this.vat ? 'vat' : status}`], {
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
        this.router.navigate(['/sale', {
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
        this.registerChangeInSales();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Sale) {
        return item.id;
    }

    registerChangeInSales() {
        this.eventSubscriber = this.eventManager.subscribe('saleListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.sales = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
