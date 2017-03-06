import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DayBookEntry } from './day-book-entry.model';
import { DayBookEntryService } from './day-book-entry.service';
@Injectable()
export class DayBookEntryPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private dayBookEntryService: DayBookEntryService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.dayBookEntryService.find(id).subscribe(dayBookEntry => {
                if (dayBookEntry.date) {
                    dayBookEntry.date = {
                        year: dayBookEntry.date.getFullYear(),
                        month: dayBookEntry.date.getMonth() + 1,
                        day: dayBookEntry.date.getDate()
                    };
                }
                this.dayBookEntryModalRef(component, dayBookEntry);
            });
        } else {
            return this.dayBookEntryModalRef(component, new DayBookEntry());
        }
    }

    dayBookEntryModalRef(component: Component, dayBookEntry: DayBookEntry): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dayBookEntry = dayBookEntry;
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
