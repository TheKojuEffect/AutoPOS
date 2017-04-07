import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Vendor } from './vendor.model';
import { VendorService } from './vendor.service';
@Injectable()
export class VendorPopupService {
    private isOpen = false;

    constructor(private modalService: NgbModal,
                private router: Router,
                private vendorService: VendorService) {
    }

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.vendorService.find(id).subscribe(vendor => {
                this.vendorModalRef(component, vendor);
            });
        } else {
            return this.vendorModalRef(component, new Vendor());
        }
    }

    vendorModalRef(component: Component, vendor: Vendor): NgbModalRef {
        let modalRef = this.modalService.open(component, {size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.vendor = vendor;
        modalRef.result.then(result => {
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.isOpen = false;
        });
        return modalRef;
    }
}
