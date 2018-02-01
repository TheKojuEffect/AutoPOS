import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Receipt } from './receipt.model';
import { ReceiptService } from './receipt.service';

@Injectable()
export class ReceiptPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private receiptService: ReceiptService

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
                this.receiptService.find(id)
                    .subscribe((receiptResponse: HttpResponse<Receipt>) => {
                        const receipt: Receipt = receiptResponse.body;
                    if (receipt.date) {
                        receipt.date = {
                            year: receipt.date.getFullYear(),
                            month: receipt.date.getMonth() + 1,
                            day: receipt.date.getDate()
                        };
                    }
                    this.ngbModalRef = this.receiptModalRef(component, receipt);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.receiptModalRef(component, new Receipt());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    receiptModalRef(component: Component, receipt: Receipt): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.receipt = receipt;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge'});
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
