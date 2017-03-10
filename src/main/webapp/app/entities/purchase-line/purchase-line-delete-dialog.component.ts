import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { PurchaseLine } from './purchase-line.model';
import { PurchaseLinePopupService } from './purchase-line-popup.service';
import { PurchaseLineService } from './purchase-line.service';

@Component({
    selector: 'apos-purchase-line-delete-dialog',
    templateUrl: './purchase-line-delete-dialog.component.html'
})
export class PurchaseLineDeleteDialogComponent {

    purchaseLine: PurchaseLine;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private purchaseLineService: PurchaseLineService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['purchaseLine']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.purchaseLineService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'purchaseLineListModification',
                content: 'Deleted an purchaseLine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'apos-purchase-line-delete-popup',
    template: ''
})
export class PurchaseLineDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private purchaseLinePopupService: PurchaseLinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.purchaseLinePopupService
                .open(PurchaseLineDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
