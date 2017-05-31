import {Component} from '@angular/core';
import {ItemService} from './item.service';
import {Observable} from 'rxjs/Observable';
import {Item} from './';

@Component({
    selector: 'apos-item-search',
    templateUrl: './item-search.component.html',
    providers: [ItemService]
})
export class ItemSearchComponent {

    selectedItem: any;
    searching = false;
    searchFailed = false;

    constructor(private itemService: ItemService) {
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

}
