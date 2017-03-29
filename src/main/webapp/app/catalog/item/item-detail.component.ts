import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Item } from './item.model';
import { ItemService } from './item.service';

@Component({
    selector: 'apos-item-detail',
    templateUrl: './item-detail.component.html'
})
export class ItemDetailComponent implements OnInit, OnDestroy {

    item: Item;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private itemService: ItemService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['item']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.itemService.find(id).subscribe(item => {
            this.item = item;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
