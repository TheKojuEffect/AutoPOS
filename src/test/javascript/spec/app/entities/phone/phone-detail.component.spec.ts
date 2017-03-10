import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PhoneDetailComponent } from '../../../../../../main/webapp/app/entities/phone/phone-detail.component';
import { PhoneService } from '../../../../../../main/webapp/app/entities/phone/phone.service';
import { Phone } from '../../../../../../main/webapp/app/entities/phone/phone.model';

describe('Component Tests', () => {

    describe('Phone Management Detail Component', () => {
        let comp: PhoneDetailComponent;
        let fixture: ComponentFixture<PhoneDetailComponent>;
        let service: PhoneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PhoneDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    PhoneService
                ]
            }).overrideComponent(PhoneDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PhoneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhoneService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Phone(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.phone).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
