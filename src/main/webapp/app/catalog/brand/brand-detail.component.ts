import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Brand } from './brand.model';
import { BrandService } from './brand.service';

@Component({
    selector: 'apos-brand-detail',
    templateUrl: './brand-detail.component.html'
})
export class BrandDetailComponent implements OnInit, OnDestroy {

    brand: Brand;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private brandService: BrandService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBrands();
    }

    load(id) {
        this.brandService.find(id)
            .subscribe((brandResponse: HttpResponse<Brand>) => {
                this.brand = brandResponse.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBrands() {
        this.eventSubscriber = this.eventManager.subscribe(
            'brandListModification',
            (response) => this.load(this.brand.id)
        );
    }
}
