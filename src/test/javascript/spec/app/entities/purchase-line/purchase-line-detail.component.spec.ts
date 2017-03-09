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
import { PurchaseLineDetailComponent } from '../../../../../../main/webapp/app/entities/purchase-line/purchase-line-detail.component';
import { PurchaseLineService } from '../../../../../../main/webapp/app/entities/purchase-line/purchase-line.service';
import { PurchaseLine } from '../../../../../../main/webapp/app/entities/purchase-line/purchase-line.model';

describe('Component Tests', () => {

    describe('PurchaseLine Management Detail Component', () => {
        let comp: PurchaseLineDetailComponent;
        let fixture: ComponentFixture<PurchaseLineDetailComponent>;
        let service: PurchaseLineService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PurchaseLineDetailComponent],
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
                    PurchaseLineService
                ]
            }).overrideComponent(PurchaseLineDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PurchaseLineDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseLineService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PurchaseLine(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.purchaseLine).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
