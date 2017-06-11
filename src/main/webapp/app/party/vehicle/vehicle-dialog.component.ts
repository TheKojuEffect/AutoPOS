import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Vehicle } from './vehicle.model';
import { VehiclePopupService } from './vehicle-popup.service';
import { VehicleService } from './vehicle.service';
import { Customer, CustomerService } from '../customer';
import { ResponseWrapper } from '../../shared/model/response-wrapper.model';
import { Observable } from 'rxjs/Observable';

@Component({
    selector: 'apos-vehicle-dialog',
    templateUrl: './vehicle-dialog.component.html'
})
export class VehicleDialogComponent implements OnInit {

    vehicle: Vehicle;
    authorities: any[];
    isSaving: boolean;

    customers: Customer[];

    constructor(public activeModal: NgbActiveModal,
                private alertService: AlertService,
                private vehicleService: VehicleService,
                private customerService: CustomerService,
                private eventManager: EventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.customerService.query()
            .subscribe((res: ResponseWrapper) => {
                this.customers = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.vehicle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.vehicleService.update(this.vehicle), false);
        } else {
            this.subscribeToSaveResponse(
                this.vehicleService.create(this.vehicle), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Vehicle>, isCreated: boolean) {
        result.subscribe((res: Vehicle) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Vehicle, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'autoPosApp.vehicle.created'
                : 'autoPosApp.vehicle.updated',
            {param: result.id}, null);

        this.eventManager.broadcast({name: 'vehicleListModification', content: 'OK'});
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
}

@Component({
    selector: 'apos-vehicle-popup',
    template: ''
})
export class VehiclePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private vehiclePopupService: VehiclePopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.modalRef = this.vehiclePopupService
                    .open(VehicleDialogComponent, params['id']);
            } else {
                this.modalRef = this.vehiclePopupService
                    .open(VehicleDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
