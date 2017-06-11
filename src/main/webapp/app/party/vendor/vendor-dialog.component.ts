import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Vendor } from './vendor.model';
import { VendorPopupService } from './vendor-popup.service';
import { VendorService } from './vendor.service';

@Component({
    selector: 'apos-vendor-dialog',
    templateUrl: './vendor-dialog.component.html'
})
export class VendorDialogComponent implements OnInit {

    vendor: Vendor;
    authorities: any[];
    isSaving: boolean;

    constructor(public activeModal: NgbActiveModal,
                private alertService: AlertService,
                private vendorService: VendorService,
                private eventManager: EventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.vendor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.vendorService.update(this.vendor), false);
        } else {
            this.subscribeToSaveResponse(
                this.vendorService.create(this.vendor), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Vendor>, isCreated: boolean) {
        result.subscribe((res: Vendor) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Vendor, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'autoPosApp.vendor.created'
                : 'autoPosApp.vendor.updated',
            {param: result.id}, null);

        this.eventManager.broadcast({name: 'vendorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}

@Component({
    selector: 'apos-vendor-popup',
    template: ''
})
export class VendorPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private vendorPopupService: VendorPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
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
