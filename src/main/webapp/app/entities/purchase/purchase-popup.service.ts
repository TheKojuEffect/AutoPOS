import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Purchase } from './purchase.model';
import { PurchaseService } from './purchase.service';
@Injectable()
export class PurchasePopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private purchaseService: PurchaseService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.purchaseService.find(id).subscribe(purchase => {
                purchase.date = this.datePipe.transform(purchase.date, 'yyyy-MM-ddThh:mm');
                this.purchaseModalRef(component, purchase);
            });
        } else {
            return this.purchaseModalRef(component, new Purchase());
        }
    }

    purchaseModalRef(component: Component, purchase: Purchase): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.purchase = purchase;
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
