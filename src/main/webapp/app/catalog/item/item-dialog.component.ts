import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Item } from './item.model';
import { ItemPopupService } from './item-popup.service';
import { ItemService } from './item.service';
import { Category, CategoryService } from '../category';
import { Brand, BrandService } from '../brand';
import { Tag, TagService } from '../tag';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'apos-item-dialog',
    templateUrl: './item-dialog.component.html'
})
export class ItemDialogComponent implements OnInit {

    item: Item;
    authorities: any[];
    isSaving: boolean;

    categories: Category[];

    brands: Brand[];

    tags: Tag[];

    constructor(public activeModal: NgbActiveModal,
                private alertService: AlertService,
                private itemService: ItemService,
                private categoryService: CategoryService,
                private brandService: BrandService,
                private tagService: TagService,
                private eventManager: EventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.categoryService.query()
            .subscribe((res: ResponseWrapper) => {
                this.categories = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.brandService.query()
            .subscribe((res: ResponseWrapper) => {
                this.brands = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.tagService.query()
            .subscribe((res: ResponseWrapper) => {
                this.tags = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.item.id !== undefined) {
            this.subscribeToSaveResponse(
                this.itemService.update(this.item), false);
        } else {
            this.subscribeToSaveResponse(
                this.itemService.create(this.item), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Item>, isCreated: boolean) {
        result.subscribe((res: Item) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Item, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'autoPosApp.item.created'
                : 'autoPosApp.item.updated',
            {param: result.id}, null);

        this.eventManager.broadcast({name: 'itemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }

    trackBrandById(index: number, item: Brand) {
        return item.id;
    }

    trackTagById(index: number, item: Tag) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'apos-item-popup',
    template: ''
})
export class ItemPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private itemPopupService: ItemPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.modalRef = this.itemPopupService
                    .open(ItemDialogComponent, params['id']);
            } else {
                this.modalRef = this.itemPopupService
                    .open(ItemDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
