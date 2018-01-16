import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DayBookEntry } from './day-book-entry.model';
import { DayBookEntryService } from './day-book-entry.service';

@Injectable()
export class DayBookEntryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private dayBookEntryService: DayBookEntryService

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
                this.dayBookEntryService.find(id).subscribe((dayBookEntry) => {
                    if (dayBookEntry.date) {
                        dayBookEntry.date = {
                            year: dayBookEntry.date.getFullYear(),
                            month: dayBookEntry.date.getMonth() + 1,
                            day: dayBookEntry.date.getDate()
                        };
                    }
                    this.ngbModalRef = this.dayBookEntryModalRef(component, dayBookEntry);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.dayBookEntryModalRef(component, new DayBookEntry());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    dayBookEntryModalRef(component: Component, dayBookEntry: DayBookEntry): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dayBookEntry = dayBookEntry;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
