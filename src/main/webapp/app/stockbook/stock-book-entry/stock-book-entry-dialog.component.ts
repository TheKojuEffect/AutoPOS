import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { StockBookEntry } from './stock-book-entry.model';
import { StockBookEntryPopupService } from './stock-book-entry-popup.service';
import { StockBookEntryService } from './stock-book-entry.service';
import { Item, ItemService } from '../../catalog/item';

@Component({
    selector: 'apos-stock-book-entry-dialog',
    templateUrl: './stock-book-entry-dialog.component.html'
})
export class StockBookEntryDialogComponent implements OnInit {

    stockBookEntry: StockBookEntry;
    isSaving: boolean;
    searching = false;
    searchFailed = false;

    noItems = false;

    constructor(public activeModal: NgbActiveModal,
                private stockBookEntryService: StockBookEntryService,
                private itemService: ItemService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.stockBookEntry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.stockBookEntryService.update(this.stockBookEntry));
        } else {
            this.subscribeToSaveResponse(
                this.stockBookEntryService.create(this.stockBookEntry));
        }
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
    //
    // onLineItemSelect = (item: Item) => {
    //     this.stockBookEntry.rate = item.markedPrice;
    // };

    private subscribeToSaveResponse(result: Observable<HttpResponse<StockBookEntry>>) {
        result.subscribe((res: HttpResponse<StockBookEntry>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: StockBookEntry) {
        this.eventManager.broadcast({name: 'stockBookEntryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'apos-stock-book-entry-popup',
    template: ''
})
export class StockBookEntryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute,
                private stockBookEntryPopupService: StockBookEntryPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.stockBookEntryPopupService
                    .open(StockBookEntryDialogComponent as Component, params['id']);
            } else {
                this.stockBookEntryPopupService
                    .open(StockBookEntryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
