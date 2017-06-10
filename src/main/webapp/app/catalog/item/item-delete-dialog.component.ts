import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Item } from './item.model';
import { ItemPopupService } from './item-popup.service';
import { ItemService } from './item.service';

@Component({
    selector: 'apos-item-delete-dialog',
    templateUrl: './item-delete-dialog.component.html'
})
export class ItemDeleteDialogComponent {

    item: Item;

    constructor(
                private itemService: ItemService,
                public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'itemListModification',
                content: 'Deleted an item'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('autoPosApp.item.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'apos-item-delete-popup',
    template: ''
})
export class ItemDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private itemPopupService: ItemPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.itemPopupService
                .open(ItemDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
