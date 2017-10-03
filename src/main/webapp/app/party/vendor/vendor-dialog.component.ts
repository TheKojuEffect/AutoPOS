import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Vendor } from './vendor.model';
import { VendorPopupService } from './vendor-popup.service';
import { VendorService } from './vendor.service';

@Component({
    selector: 'apos-vendor-dialog',
    templateUrl: './vendor-dialog.component.html'
})
export class VendorDialogComponent implements OnInit {

    vendor: Vendor;
    isSaving: boolean;

    constructor(public activeModal: NgbActiveModal,
                private alertService: JhiAlertService,
                private vendorService: VendorService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.vendor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.vendorService.update(this.vendor));
        } else {
            this.subscribeToSaveResponse(
                this.vendorService.create(this.vendor));
        }
    }

    private subscribeToSaveResponse(result: Observable<Vendor>) {
        result.subscribe((res: Vendor) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Vendor) {
        this.eventManager.broadcast({name: 'vendorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'apos-vendor-popup',
    template: ''
})
export class VendorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute,
                private vendorPopupService: VendorPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.vendorPopupService
                    .open(VendorDialogComponent as Component, params['id']);
            } else {
                this.vendorPopupService
                    .open(VendorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
