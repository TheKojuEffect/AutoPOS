import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Category } from './category.model';
import { CategoryPopupService } from './category-popup.service';
import { CategoryService } from './category.service';

@Component({
    selector: 'apos-category-delete-dialog',
    templateUrl: './category-delete-dialog.component.html'
})
export class CategoryDeleteDialogComponent {

    category: Category;

    constructor(private categoryService: CategoryService,
                public activeModal: NgbActiveModal,
                private alertService: JhiAlertService,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.categoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'categoryListModification',
                content: 'Deleted an category'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('autoPosApp.category.deleted', {param: id}, null);
    }
}

@Component({
    selector: 'apos-category-delete-popup',
    template: ''
})
export class CategoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private categoryPopupService: CategoryPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.categoryPopupService
                .open(CategoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
