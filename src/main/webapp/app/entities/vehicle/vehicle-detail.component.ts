import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Vehicle } from './vehicle.model';
import { VehicleService } from './vehicle.service';

@Component({
    selector: 'apos-vehicle-detail',
    templateUrl: './vehicle-detail.component.html'
})
export class VehicleDetailComponent implements OnInit, OnDestroy {

    vehicle: Vehicle;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private vehicleService: VehicleService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['vehicle']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.vehicleService.find(id).subscribe(vehicle => {
            this.vehicle = vehicle;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
