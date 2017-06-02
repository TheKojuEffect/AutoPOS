import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {Sale} from './sale.model';
import {SaleService} from './sale.service';
import {ItemService} from '../../catalog/item/item.service';
import {Item} from '../../catalog/item/item.model';
import {Observable} from 'rxjs/Observable';
import {SaleLine} from '../sale-line/sale-line.model';
import {NgbTypeaheadSelectItemEvent} from '@ng-bootstrap/ng-bootstrap';

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
    searchFailed = false;
    line = new SaleLine();

    constructor(private eventManager: EventManager,
                private jhiLanguageService: JhiLanguageService,
                private saleService: SaleService,
                private itemService: ItemService,
                private route: ActivatedRoute) {
        this.jhiLanguageService.setLocations(['sale', 'saleStatus']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInSales();
    }

    load(id) {
        this.saleService.find(id).subscribe(sale => {
            this.sale = sale;
        });
    }

    previousState() {
        window.history.back();
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
            .do(() => this.searching = true)
            .switchMap(term =>
                this.itemService.search(term)
                    .do(() => this.searchFailed = false)
                    .catch(() => {
                        this.searchFailed = true;
                        return Observable.of([]);
                    }))
            .do(() => this.searching = false);


    itemFormatter = (item: Item) => item.name;

    onLineItemSelect = (item: Item) => {
        this.line.rate = item.markedPrice;
    }

}
