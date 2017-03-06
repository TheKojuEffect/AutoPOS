import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Phone } from './phone.model';
import { PhonePopupService } from './phone-popup.service';
import { PhoneService } from './phone.service';
@Component({
    selector: 'apos-phone-dialog',
    templateUrl: './phone-dialog.component.html'
})
export class PhoneDialogComponent implements OnInit {

    phone: Phone;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private phoneService: PhoneService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['phone']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.phone.id !== undefined) {
            this.phoneService.update(this.phone)
                .subscribe((res: Phone) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.phoneService.create(this.phone)
                .subscribe((res: Phone) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Phone) {
        this.eventManager.broadcast({ name: 'phoneListModification', content: 'OK'});
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
}

@Component({
    selector: 'apos-phone-popup',
    template: ''
})
export class PhonePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private phonePopupService: PhonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.phonePopupService
                    .open(PhoneDialogComponent, params['id']);
            } else {
                this.modalRef = this.phonePopupService
                    .open(PhoneDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
