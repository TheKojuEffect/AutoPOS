import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PurchaseLine } from './purchase-line.model';
import { PurchaseLineService } from './purchase-line.service';
@Injectable()
export class PurchaseLinePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private purchaseLineService: PurchaseLineService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.purchaseLineService.find(id).subscribe(purchaseLine => {
                this.purchaseLineModalRef(component, purchaseLine);
            });
        } else {
            return this.purchaseLineModalRef(component, new PurchaseLine());
        }
    }

    purchaseLineModalRef(component: Component, purchaseLine: PurchaseLine): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.purchaseLine = purchaseLine;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
