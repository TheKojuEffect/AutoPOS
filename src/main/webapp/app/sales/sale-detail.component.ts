import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';
import { Sale } from './sale.model';
import { SaleService } from './sale.service';
import { HttpResponse } from '@angular/common/http';

import { ItemService } from '../catalog/item/item.service';
import { Item } from '../catalog/item/item.model';
import { Observable } from 'rxjs/Rx';
import { SaleLine } from './sale-line.model';
import { SaleLineService } from './sale-line.service';

import * as _ from 'lodash';
import { Vehicle } from '../party/vehicle/vehicle.model';
import { VehicleService } from '../party/vehicle/vehicle.service';

@Component({
    selector: 'apos-sale-detail',
    templateUrl: './sale-detail.component.html',
    providers: [
        ItemService
    ]
})
export class SaleDetailComponent implements OnInit, OnDestroy {

    sale: Sale;
    private subscription: any;
    private eventSubscriber: Subscription;
    searching = false;
    noItems = false;
    searchingVehicle = false;
    searchFailed = false;
    searchVehicleFailed = false;
    line = new SaleLine();

    constructor(private eventManager: JhiEventManager,
                private saleService: SaleService,
                private saleLineService: SaleLineService,
                private itemService: ItemService,
                private vehicleService: VehicleService,
                private router: Router,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInSales();
    }

    load(id) {
        this.saleService.find(id)
            .subscribe((saleResponse: HttpResponse<Sale>) => {
                this.sale = saleResponse.body;
            });
    }

    gotoSaleList() {
        this.router.navigate([this.sale.vat ? './sale/vat' : './sale/pending']);
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSales() {
        this.eventSubscriber = this.eventManager.subscribe('saleListModification', response => this.load(this.sale.id));
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

    onLineItemSelect = (item: Item) => {
        this.line.rate = item.markedPrice;
    };

    searchVehicle = (text: Observable<string>) =>
        text.debounceTime(200)
            .distinctUntilChanged()
            .do(() => this.searchingVehicle = true)
            .switchMap(term =>
                this.vehicleService.search(term)
                    .do(() => this.searchVehicleFailed = false)
                    .catch(() => {
                        this.searchVehicleFailed = true;
                        return Observable.of([]);
                    }))
            .do(() => this.searchingVehicle = false);

    vehicleFormatter = (vehicle: Vehicle) => vehicle.number;

    onLineItemSubmit() {
        this.line.sale = this.sale;
        if (this.line.id) {

            const lineIndex = _.findIndex(this.sale.lines,
                line => line.id === this.line.id);

            this.saleLineService.update(
                this.sale.id,
                this.line.id,
                this.line
            ).subscribe((line) => this.sale.lines.splice(lineIndex, 1, line.body));

        } else {
            this.saleLineService.create(
                this.sale.id,
                this.line
            ).subscribe((line) => this.sale.lines.push(line.body));
        }
        this.resetLineItem();
    };

    resetLineItem() {
        this.line = new SaleLine();
    };

    editSaleLine(line: SaleLine) {
        this.line = Object.assign({}, line);
    };

    get subTotal(): number {
        return _.sumBy(this.sale.lines, 'amount');
    }

    get total(): number {
        return this.subTotal - this.sale.discount;
    }

    get hasLines(): boolean {
        return this.sale.lines && this.sale.lines.length > 0;
    }

    deleteSale() {
        const sure = window.confirm('Are you sure you want to delete this sale?');
        if (sure) {
            this.saleService.delete(this.sale.id).subscribe(() => {
                this.gotoSaleList();
            });
        }
    }

    updateSale() {
        this.saleService.update(this.sale)
            .subscribe(() => this.gotoSaleList());
    }

    deleteSaleLine() {
        const sure = window.confirm('Are you sure you want to delete this sale item?');
        if (sure) {
            this.saleLineService.delete(this.sale.id, this.line.id)
                .subscribe(() => {
                    const lineIndex = _.findIndex(this.sale.lines,
                        line => line.id === this.line.id);
                    this.sale.lines.splice(lineIndex, 1);
                    this.resetLineItem();
                });
        }
    }

}
