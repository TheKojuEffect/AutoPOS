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
import { PriceHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/price-history/price-history-detail.component';
import { PriceHistoryService } from '../../../../../../main/webapp/app/entities/price-history/price-history.service';
import { PriceHistory } from '../../../../../../main/webapp/app/entities/price-history/price-history.model';

describe('Component Tests', () => {

    describe('PriceHistory Management Detail Component', () => {
        let comp: PriceHistoryDetailComponent;
        let fixture: ComponentFixture<PriceHistoryDetailComponent>;
        let service: PriceHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PriceHistoryDetailComponent],
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
                    PriceHistoryService
                ]
            }).overrideComponent(PriceHistoryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PriceHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriceHistoryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PriceHistory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.priceHistory).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
