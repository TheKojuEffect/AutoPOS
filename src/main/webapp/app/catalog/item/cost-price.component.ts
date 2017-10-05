import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { Item } from './item.model';
import { ItemPopupService } from './item-popup.service';
import { ItemService } from './item.service';
import { CostPriceInfo } from './CostPriceInfo';

@Component({
    selector: 'apos-cost-price-dialog',
    templateUrl: './cost-price.component.html'
})
export class CostPriceDialogComponent implements OnInit {

    item: Item;
    costPrices: CostPriceInfo[];

    constructor(private itemService: ItemService,
                public activeModal: NgbActiveModal,) {
    }

    ngOnInit(): void {
        this.itemService.getCostPrices(this.item.id)
            .subscribe((response) => {

                this.costPrices = response as CostPriceInfo[];
            });
    }

    close() {
        this.activeModal.dismiss('cancel');
    }

}

@Component({
    selector: 'apos-cost-price-popup',
    template: ''
})
export class CostPricePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private itemPopupService: ItemPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.itemPopupService.open(CostPriceDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
