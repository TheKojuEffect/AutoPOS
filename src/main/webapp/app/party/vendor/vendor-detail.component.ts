import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Vendor } from './vendor.model';
import { VendorService } from './vendor.service';

@Component({
    selector: 'apos-vendor-detail',
    templateUrl: './vendor-detail.component.html'
})
export class VendorDetailComponent implements OnInit, OnDestroy {

    vendor: Vendor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private vendorService: VendorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVendors();
    }

    load(id) {
        this.vendorService.find(id)
            .subscribe((vendorResponse: HttpResponse<Vendor>) => {
                this.vendor = vendorResponse.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVendors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'vendorListModification',
            (response) => this.load(this.vendor.id)
        );
    }
}
