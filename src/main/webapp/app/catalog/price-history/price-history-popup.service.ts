import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PriceHistory } from './price-history.model';
import { PriceHistoryService } from './price-history.service';

@Injectable()
export class PriceHistoryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private priceHistoryService: PriceHistoryService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.priceHistoryService.find(id).subscribe((priceHistory) => {
                    priceHistory.date = this.datePipe
                        .transform(priceHistory.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.priceHistoryModalRef(component, priceHistory);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.priceHistoryModalRef(component, new PriceHistory());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    priceHistoryModalRef(component: Component, priceHistory: PriceHistory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.priceHistory = priceHistory;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
