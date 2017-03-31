import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Phone } from './phone.model';
import { PhoneService } from './phone.service';

@Component({
    selector: 'apos-phone-detail',
    templateUrl: './phone-detail.component.html'
})
export class PhoneDetailComponent implements OnInit, OnDestroy {

    phone: Phone;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private phoneService: PhoneService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['phone']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.phoneService.find(id).subscribe(phone => {
            this.phone = phone;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
