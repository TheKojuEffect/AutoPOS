import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Category } from './category.model';
import { CategoryService } from './category.service';

@Component({
    selector: 'apos-category-detail',
    templateUrl: './category-detail.component.html'
})
export class CategoryDetailComponent implements OnInit, OnDestroy {

    category: Category;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private categoryService: CategoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCategories();
    }

    load(id) {
        this.categoryService.find(id)
            .subscribe((categoryResponse: HttpResponse<Category>) => {
                this.category = categoryResponse.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'categoryListModification',
            (response) => this.load(this.category.id)
        );
    }
}
