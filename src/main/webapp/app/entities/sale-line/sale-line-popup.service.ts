import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SaleLine } from './sale-line.model';
import { SaleLineService } from './sale-line.service';
@Injectable()
export class SaleLinePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private saleLineService: SaleLineService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.saleLineService.find(id).subscribe(saleLine => {
                this.saleLineModalRef(component, saleLine);
            });
        } else {
            return this.saleLineModalRef(component, new SaleLine());
        }
    }

    saleLineModalRef(component: Component, saleLine: SaleLine): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.saleLine = saleLine;
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
