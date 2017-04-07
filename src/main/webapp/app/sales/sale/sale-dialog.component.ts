import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager, JhiLanguageService } from 'ng-jhipster';

import { Sale } from './sale.model';
import { SalePopupService } from './sale-popup.service';
import { SaleService } from './sale.service';
import { Customer, CustomerService } from '../../party/customer';
import { Vehicle, VehicleService } from '../../party/vehicle';
import { SaleLine, SaleLineService } from '../sale-line';

@Component({
    selector: 'apos-sale-dialog',
    templateUrl: './sale-dialog.component.html'
})
export class SaleDialogComponent implements OnInit {

    sale: Sale;
    authorities: any[];
    isSaving: boolean;

    customers: Customer[];

    salelines: SaleLine[];

    vehicles: Vehicle[];

    constructor(public activeModal: NgbActiveModal,
                private jhiLanguageService: JhiLanguageService,
                private alertService: AlertService,
                private saleService: SaleService,
                private customerService: CustomerService,
                private saleLineService: SaleLineService,
                private vehicleService: VehicleService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['sale', 'saleStatus']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.customerService.query().subscribe(
            (res: Response) => {
                this.customers = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.saleLineService.query().subscribe(
            (res: Response) => {
                this.salelines = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.vehicleService.query().subscribe(
            (res: Response) => {
                this.vehicles = res.json();
            }, (res: Response) => this.onError(res.json()));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sale.id !== undefined) {
            this.saleService.update(this.sale)
                .subscribe((res: Sale) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.saleService.create(this.sale)
                .subscribe((res: Sale) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Sale) {
        this.eventManager.broadcast({name: 'saleListModification', content: 'OK'});
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

    trackCustomerById(index: number, item: Customer) {
        return item.id;
    }

    trackSaleLineById(index: number, item: SaleLine) {
        return item.id;
    }

    trackVehicleById(index: number, item: Vehicle) {
        return item.id;
    }
}

@Component({
    selector: 'apos-sale-popup',
    template: ''
})
export class SalePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private salePopupService: SalePopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.modalRef = this.salePopupService
                    .open(SaleDialogComponent, params['id']);
            } else {
                this.modalRef = this.salePopupService
                    .open(SaleDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
