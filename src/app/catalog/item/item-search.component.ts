import { Component } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Item } from './item';
import { ItemService } from './item.service';
import { TypeaheadMatch } from 'ng2-bootstrap';

@Component({
    selector: 'apos-item-search',
    templateUrl: './item-search.component.html'
})
export class ItemSearchComponent {

    searchTerm: string;
    items: Observable<Item[]>;
    loading: boolean;
    noResults: boolean;

    constructor(private itemService: ItemService) {
        this.items = Observable.create((observer: any) => {
            observer.next(this.searchTerm);
        }).mergeMap((token: string) => this.itemService.search(token));

    }

    itemSelected(e: TypeaheadMatch): void {
        const item = e.item;
        console.log(item);
    }
}

