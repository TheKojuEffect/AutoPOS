import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Brand } from './brand.model';
import { BrandPopupService } from './brand-popup.service';
import { BrandService } from './brand.service';
import { Item, ItemService } from '../item';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'apos-brand-dialog',
    templateUrl: './brand-dialog.component.html'
})
export class BrandDialogComponent implements OnInit {

    brand: Brand;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private brandService: BrandService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.brand.id !== undefined) {
            this.subscribeToSaveResponse(
                this.brandService.update(this.brand));
        } else {
            this.subscribeToSaveResponse(
                this.brandService.create(this.brand));
        }
    }

    private subscribeToSaveResponse(result: Observable<Brand>) {
        result.subscribe((res: Brand) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Brand) {
        this.eventManager.broadcast({ name: 'brandListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackItemById(index: number, item: Item) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'apos-brand-popup',
    template: ''
})
export class BrandPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private brandPopupService: BrandPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.brandPopupService
                    .open(BrandDialogComponent as Component, params['id']);
            } else {
                this.brandPopupService
                    .open(BrandDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
