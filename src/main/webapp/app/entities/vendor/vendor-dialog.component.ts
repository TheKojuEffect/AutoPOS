import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Vendor } from './vendor.model';
import { VendorPopupService } from './vendor-popup.service';
import { VendorService } from './vendor.service';
import { Phone, PhoneService } from '../phone';
@Component({
    selector: 'apos-vendor-dialog',
    templateUrl: './vendor-dialog.component.html'
})
export class VendorDialogComponent implements OnInit {

    vendor: Vendor;
    authorities: any[];
    isSaving: boolean;

    phones: Phone[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private vendorService: VendorService,
        private phoneService: PhoneService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['vendor']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.phoneService.query().subscribe(
            (res: Response) => { this.phones = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.vendor.id !== undefined) {
            this.vendorService.update(this.vendor)
                .subscribe((res: Vendor) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.vendorService.create(this.vendor)
                .subscribe((res: Vendor) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Vendor) {
        this.eventManager.broadcast({ name: 'vendorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackPhoneById(index: number, item: Phone) {
        return item.id;
    }
}

@Component({
    selector: 'apos-vendor-popup',
    template: ''
})
export class VendorPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private vendorPopupService: VendorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.vendorPopupService
                    .open(VendorDialogComponent, params['id']);
            } else {
                this.modalRef = this.vendorPopupService
                    .open(VendorDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
