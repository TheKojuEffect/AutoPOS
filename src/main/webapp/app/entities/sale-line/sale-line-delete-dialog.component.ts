import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { SaleLine } from './sale-line.model';
import { SaleLinePopupService } from './sale-line-popup.service';
import { SaleLineService } from './sale-line.service';

@Component({
    selector: 'apos-sale-line-delete-dialog',
    templateUrl: './sale-line-delete-dialog.component.html'
})
export class SaleLineDeleteDialogComponent {

    saleLine: SaleLine;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private saleLineService: SaleLineService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['saleLine']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.saleLineService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'saleLineListModification',
                content: 'Deleted an saleLine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'apos-sale-line-delete-popup',
    template: ''
})
export class SaleLineDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private saleLinePopupService: SaleLinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.saleLinePopupService
                .open(SaleLineDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
