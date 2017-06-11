import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Vendor } from './vendor.model';
import { VendorPopupService } from './vendor-popup.service';
import { VendorService } from './vendor.service';

@Component({
    selector: 'apos-vendor-delete-dialog',
    templateUrl: './vendor-delete-dialog.component.html'
})
export class VendorDeleteDialogComponent {

    vendor: Vendor;

    constructor(private vendorService: VendorService,
                public activeModal: NgbActiveModal,
                private alertService: AlertService,
                private eventManager: EventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vendorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'vendorListModification',
                content: 'Deleted an vendor'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('autoPosApp.vendor.deleted', {param: id}, null);
    }
}

@Component({
    selector: 'apos-vendor-delete-popup',
    template: ''
})
export class VendorDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private vendorPopupService: VendorPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.vendorPopupService
                .open(VendorDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
