import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Vehicle } from './vehicle.model';
import { VehicleService } from './vehicle.service';
@Injectable()
export class VehiclePopupService {
    private isOpen = false;

    constructor(private modalService: NgbModal,
                private router: Router,
                private vehicleService: VehicleService) {
    }

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.vehicleService.find(id).subscribe((vehicle) => {
                this.vehicleModalRef(component, vehicle);
            });
        } else {
            return this.vehicleModalRef(component, new Vehicle());
        }
    }

    vehicleModalRef(component: Component, vehicle: Vehicle): NgbModalRef {
        const modalRef = this.modalService.open(component, {size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.vehicle = vehicle;
        modalRef.result.then((result) => {
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.isOpen = false;
        });
        return modalRef;
    }
}
