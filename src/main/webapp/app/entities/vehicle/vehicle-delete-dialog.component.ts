import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Vehicle } from './vehicle.model';
import { VehiclePopupService } from './vehicle-popup.service';
import { VehicleService } from './vehicle.service';

@Component({
    selector: 'apos-vehicle-delete-dialog',
    templateUrl: './vehicle-delete-dialog.component.html'
})
export class VehicleDeleteDialogComponent {

    vehicle: Vehicle;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private vehicleService: VehicleService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['vehicle']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.vehicleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'vehicleListModification',
                content: 'Deleted an vehicle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'apos-vehicle-delete-popup',
    template: ''
})
export class VehicleDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private vehiclePopupService: VehiclePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.vehiclePopupService
                .open(VehicleDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
