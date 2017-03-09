import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Sale } from './sale.model';
import { SalePopupService } from './sale-popup.service';
import { SaleService } from './sale.service';

@Component({
    selector: 'apos-sale-delete-dialog',
    templateUrl: './sale-delete-dialog.component.html'
})
export class SaleDeleteDialogComponent {

    sale: Sale;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private saleService: SaleService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['sale', 'saleStatus']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.saleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'saleListModification',
                content: 'Deleted an sale'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'apos-sale-delete-popup',
    template: ''
})
export class SaleDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private salePopupService: SalePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.salePopupService
                .open(SaleDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
