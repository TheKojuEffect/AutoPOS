import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Vendor } from './vendor.model';
import { VendorService } from './vendor.service';

@Component({
    selector: 'apos-vendor-detail',
    templateUrl: './vendor-detail.component.html'
})
export class VendorDetailComponent implements OnInit, OnDestroy {

    vendor: Vendor;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private vendorService: VendorService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['vendor']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.vendorService.find(id).subscribe(vendor => {
            this.vendor = vendor;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
