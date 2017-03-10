import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PriceHistory } from './price-history.model';
import { PriceHistoryService } from './price-history.service';
@Injectable()
export class PriceHistoryPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private priceHistoryService: PriceHistoryService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.priceHistoryService.find(id).subscribe(priceHistory => {
                priceHistory.date = this.datePipe
                    .transform(priceHistory.date, 'yyyy-MM-ddThh:mm');
                this.priceHistoryModalRef(component, priceHistory);
            });
        } else {
            return this.priceHistoryModalRef(component, new PriceHistory());
        }
    }

    priceHistoryModalRef(component: Component, priceHistory: PriceHistory): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.priceHistory = priceHistory;
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
