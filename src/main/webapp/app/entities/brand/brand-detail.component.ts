import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Brand } from './brand.model';
import { BrandService } from './brand.service';

@Component({
    selector: 'apos-brand-detail',
    templateUrl: './brand-detail.component.html'
})
export class BrandDetailComponent implements OnInit, OnDestroy {

    brand: Brand;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private brandService: BrandService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['brand']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.brandService.find(id).subscribe(brand => {
            this.brand = brand;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
