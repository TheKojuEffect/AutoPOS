import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager, JhiLanguageService } from 'ng-jhipster';

import { Item } from './item.model';
import { ItemPopupService } from './item-popup.service';
import { ItemService } from './item.service';
import { Category, CategoryService } from '../category';
import { Brand, BrandService } from '../brand';
import { Tag } from '../tag/tag.model';
import { TagService } from '../tag';

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
                private jhiLanguageService: JhiLanguageService,
                private alertService: AlertService,
                private itemService: ItemService,
                private categoryService: CategoryService,
                private brandService: BrandService,
                private tagService: TagService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['item']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.categoryService.query().subscribe(
            (res: Response) => {
                this.categories = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.brandService.query().subscribe(
            (res: Response) => {
                this.brands = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.tagService.query().subscribe(
            (res: Response) => {
                this.tags = res.json();
            }, (res: Response) => this.onError(res.json()));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.item.id !== undefined) {
            this.itemService.update(this.item)
                .subscribe((res: Item) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.itemService.create(this.item)
                .subscribe((res: Item) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: Item) {
        this.eventManager.broadcast({name: 'itemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
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
        this.routeSub = this.route.params.subscribe(params => {
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
